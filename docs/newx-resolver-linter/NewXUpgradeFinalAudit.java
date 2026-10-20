import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import com.android.tools.smali.dexlib2.*;
import com.android.tools.smali.dexlib2.iface.*;
import com.android.tools.smali.dexlib2.iface.instruction.*;
import com.android.tools.smali.dexlib2.iface.reference.*;

/** Exact final-APK evidence for reordered parameters, opt-out flow and experiment withdrawal. */
public class NewXUpgradeFinalAudit extends FixedRefreshStaticReview {
 static final String MOD="Landroidx/compose/ui/Modifier;", COM="Landroidx/compose/runtime/Composer;";
 static List<String> types(MethodReference m){return m.getParameterTypes().stream().map(Object::toString).toList();}
 static boolean has(Method m,String key){return code(m).stream().anyMatch(i->i instanceof ReferenceInstruction r&&r.getReference() instanceof StringReference s&&s.getString().equals(key));}
 static List<Method> all(){return classes.values().stream().flatMap(c->StreamSupport.stream(c.getMethods().spliterator(),false)).map(m->(Method)m).toList();}
 static Method unique(List<Method> list,String label){check(list.size()==1,label+" unique: "+list);return list.get(0);}
 static int parameter(Method m,String type){var ts=types(m);check(Collections.frequency(ts,type)==1,"Unique "+type+" in "+m);return m.getImplementation().getRegisterCount()-ts.size()+ts.indexOf(type);}
 static void guard(Method m,Map<String,ClassDef> before,boolean pager){
  var now=code(m);var old=code(exact(before,m));int extra=now.size()-old.size();
  check(extra>0,"Added setting guard: "+m);
  var skip=IntStream.range(0,extra).boxed().collect(Collectors.toSet());
  check(normalized(now,skip).equals(normalized(old,Set.of())),"Original body, registers and internal branches unchanged: "+m);
  var gates=IntStream.range(0,extra).filter(i->now.get(i).getOpcode()==Opcode.IF_EQZ&&target(now,i)==extra).boxed().toList();
  check(gates.size()==1,"Disabled setting reaches the exact original first instruction: "+m);
  int bool=parameter(m,"Z");
  check(IntStream.range(0,extra).anyMatch(i->now.get(i) instanceof NarrowLiteralInstruction l&&l.getNarrowLiteral()==0&&now.get(i) instanceof OneRegisterInstruction r&&r.getRegisterA()==bool),"Enabled setting clears the actual boolean operand v"+bool);
  if(pager){
   int mask=m.getImplementation().getRegisterCount()-1;
   check(IntStream.range(0,extra).anyMatch(i->now.get(i).getOpcode()==Opcode.AND_INT&&now.get(i) instanceof ThreeRegisterInstruction r&&r.getRegisterA()==mask&&r.getRegisterB()==mask),"Clears the actual trailing default mask");
   check(now.subList(0,extra).stream().anyMatch(i->i instanceof NarrowLiteralInstruction l&&l.getNarrowLiteral()==-0x101),"User-scroll default bit is cleared so Compose cannot restore true");
  }
 }
 public static void main(String[] args)throws Exception{
  classes.putAll(load(args[0]));var before=load(args[1]);var all=all();
  Method pager=unique(all.stream().filter(m->has(m,"newx.post_actions_media.disable_video_scrolling")&&types(m).stream().anyMatch(t->t.startsWith("Landroidx/compose/foundation/pager/"))).toList(),"VerticalPager");
  guard(pager,before,true);
  Method divider=unique(all.stream().filter(m->has(m,"newx.timeline.hide_post_dividers")&&types(m).size()==6&&types(m).contains(MOD)&&types(m).contains("Z")&&types(m).contains(COM)).toList(),"Shared post divider");
  guard(divider,before,false);
  check(all.stream().anyMatch(m->has(m,"newx.post_actions_media.force_highest_video_quality")),"Original highest-resolution setting remains reachable");
  check(classes.keySet().stream().noneMatch(t->t.contains("PreferHevcPlayback")),"No experimental HEVC implementation in final APK");
  check(all.stream().noneMatch(m->has(m,"newx.post_actions_media.prefer_hevc_playback")||has(m,"piko_newx_prefer_hevc_playback_title")),"No experimental HEVC setting in final DEX");
  var report=new LinkedHashMap<String,Object>();report.put("apk",args[0]);report.put("checks",checks);report.put("passed",true);
  Files.writeString(Path.of(args[2]),json(report)+"\n");System.out.println("Passed "+checks.size()+" upgrade guard/withdrawal checks");
 }
}
