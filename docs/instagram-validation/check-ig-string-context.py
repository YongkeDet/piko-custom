#!/usr/bin/env python3
"""Exercise the real IgStr against a downloaded-language resource wrapper.

The host deliberately resolves valid IDs to unrelated button labels. Piko strings must
come from the unwrapped package resources, as before .15, rather than that host table.
No emulator, Android jar replacement in the app, or test-only production branch is used.
"""
from pathlib import Path
import argparse
import json
import os
import shutil
import subprocess
import tempfile
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[2]
READER = ROOT / 'extensions/instagram/src/main/java/app/morphe/extension/instagram/utils/IgStr.java'
parser = argparse.ArgumentParser()
parser.add_argument('--source', type=Path, default=READER)
parser.add_argument('--temp-dir', type=Path)
args = parser.parse_args()
resources = ROOT / 'patches/src/main/resources/addresources/values-zh-rCN/instagram/strings.xml'
labels = {e.attrib['name']: ''.join(e.itertext()) for e in ET.parse(resources).getroot() if e.tag == 'string'}
def quote(s): return json.dumps(s, ensure_ascii=True)
sources = {
 'android/content/res/Resources.java': '''package android.content.res;
import java.util.*;
public class Resources {
 public final Map<String,Integer> ids = new HashMap<>();
 public final Map<Integer,String> text = new HashMap<>();
 public int reads;
 public int getIdentifier(String name,String type,String pkg) { return ids.getOrDefault(name,0); }
 public String getString(int id) { reads++; if(!text.containsKey(id))throw new IllegalArgumentException(); return text.get(id); }
}''',
 'android/content/Context.java': '''package android.content;
import android.content.res.Resources;
public abstract class Context {
 public abstract Resources getResources();
 public String getPackageName() { return "com.instagram.android"; }
 public final String getString(int id) { return getResources().getString(id); }
}''',
 'android/content/ContextWrapper.java': '''package android.content;
import android.content.res.Resources;
public class ContextWrapper extends Context {
 private final Context base;
 public ContextWrapper(Context base) { this.base=base; }
 public Context getBaseContext() { return base; }
 public Resources getResources() { return base.getResources(); }
}''',
 'android/app/Activity.java': '''package android.app;
import android.content.*;
public class Activity extends ContextWrapper {
 public Activity(Context base) { super(base); }
 public boolean isDestroyed() { return false; }
 public boolean isFinishing() { return false; }
}''',
 'app/morphe/extension/shared/Utils.java': '''package app.morphe.extension.shared;
import android.app.Activity;import android.content.Context;
public class Utils {
 public static Context context; public static Activity activity;
 public static Context getContext() { return context; }
 public static Activity getActivity() { return activity; }
}''',
}
entries = '\n'.join(f'labels.put({quote(k)}, {quote(v)});' for k,v in labels.items())
sources['ContextRegression.java'] = '''import java.util.*;
import android.app.Activity;import android.content.*;import android.content.res.Resources;
import app.morphe.extension.shared.Utils;import app.morphe.extension.instagram.utils.IgStr;
public class ContextRegression {
 public static void main(String[] args) {
  Map<String,String> labels = new LinkedHashMap<>();
''' + entries + '''
  Resources raw = new Resources(), downloaded = new Resources();
  List<String> values = new ArrayList<>(labels.values());int id=1000,index=0;
  for(var e:labels.entrySet()) {
   raw.ids.put(e.getKey(),id);raw.text.put(id,e.getValue());
   downloaded.ids.put(e.getKey(),id);downloaded.text.put(id,values.get((index+73)%values.size()));id++;index++;
  }
  Context base = new Context() { public Resources getResources() { return raw; } };
  Context application = new ContextWrapper(base) { public Resources getResources() { return downloaded; } };
  Utils.context = new ContextWrapper(application);
  Utils.activity = new Activity(application);
  int wrong=0;
  for(var e:labels.entrySet()) {
   String actual=IgStr.str(e.getKey());
   if(!e.getValue().equals(actual)) { if(wrong<3)System.out.println(e.getKey()+": incorrect host-language label");wrong++; }
  }
  if(wrong!=0)throw new AssertionError("Mismatched button/resource labels: "+wrong+" / "+labels.size());
  if(downloaded.reads!=0)throw new AssertionError("Piko text reached the host downloaded-language table");
  if(!IgStr.str("piko_selected_count",2).equals("\u5df2\u9009\u62e9 2 \u9879"))throw new AssertionError("Formatted label changed");
  if(!IgStr.str("piko_missing_fixture").equals("piko_missing_fixture"))throw new AssertionError("Unknown-key fallback changed");
  int first=raw.ids.get("piko_category_ads");raw.text.put(first,"Updated package resource");
  if(!IgStr.str("piko_category_ads").equals("Updated package resource"))throw new AssertionError("Updated package text was not read");
  System.out.println("Passed "+labels.size()+" label bindings; host remapping bypassed; formatting and resource refresh preserved");
 }
}'''
java_home = os.environ.get('JAVA_HOME')
def executable(name):
    suffix = '.exe' if os.name == 'nt' else ''
    candidate = Path(java_home) / 'bin' / (name + suffix) if java_home else None
    return str(candidate) if candidate and candidate.is_file() else shutil.which(name) or name
if args.temp_dir: args.temp_dir.mkdir(parents=True,exist_ok=True)
with tempfile.TemporaryDirectory(prefix='ig-string-context-',dir=args.temp_dir) as folder:
    folder=Path(folder)
    for name,content in sources.items():
        target=folder/name;target.parent.mkdir(parents=True,exist_ok=True)
        target.write_text(content,encoding='utf-8',newline='\n')
    target=folder/'app/morphe/extension/instagram/utils/IgStr.java';target.parent.mkdir(parents=True,exist_ok=True)
    target.write_text(args.source.read_text(encoding='utf-8'),encoding='utf-8',newline='\n')
    subprocess.run([executable('javac'),'-encoding','UTF-8','-d',str(folder/'classes'),*[str(p) for p in folder.rglob('*.java')]],check=True)
    subprocess.run([executable('java'),'-cp',str(folder/'classes'),'ContextRegression'],check=True)
