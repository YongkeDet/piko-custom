import com.android.tools.smali.dexlib2.*;
import com.android.tools.smali.dexlib2.iface.*;
import com.android.tools.smali.dexlib2.iface.instruction.*;
import com.android.tools.smali.dexlib2.iface.reference.*;
import com.reandroid.apk.ApkModule;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/** Offline checks over the emitted APK, not a second patch implementation. */
public final class InstagramDownloadStaticReview extends FixedRefreshStaticReview {
 static int followMoves(List<Instruction> body,int start,int end,int register) {
  for(int i=end-1;i>=start;i--){var x=body.get(i);if(x.getOpcode().name().startsWith("MOVE_OBJECT")&&x instanceof TwoRegisterInstruction move&&move.getRegisterA()==register)register=move.getRegisterB();}
  return register;
 }
 static void feedButtonChecks(Map<String,ClassDef> before) {
  String button=ROOT+"FeedDownloadButton;", session="Lcom/instagram/common/session/UserSession;";
  var entries=new ArrayList<Method>();for(ClassDef c:classes.values())if(before.containsKey(c.getType()))for(Method m:c.getMethods())if(call(m,button,"add"))entries.add(m);
  check(entries.size()==1,"One native feed component builder inserts the footer download button");
  Method entry=entries.get(0),original=exact(before,entry);var body=code(entry);int hook=index(body,button,"add");
  check(calls(entry).stream().filter(r->!r.getDefiningClass().equals(button)).map(Object::toString).toList().equals(calls(original).stream().map(Object::toString).toList()),"Footer injection retains every original native call");
  check(entry.getImplementation().getRegisterCount()==original.getImplementation().getRegisterCount(),"Footer injection preserves the original register frame");
  var factory=ref(body.get(hook+1));var args=regs(body.get(hook));var factoryArgs=regs(body.get(hook+1));
  check(factory.getParameterTypes().size()==7&&factory.getParameterTypes().get(4).toString().equals(session),"Download insertion runs immediately before the native save component factory");
  int start=hook;while(start>0&&body.get(start-1).getOpcode().name().startsWith("MOVE"))start--;
  check(followMoves(body,start,hook,args[0])==followMoves(body,start,hook,factoryArgs[1])&&followMoves(body,start,hook,args[2])==followMoves(body,start,hook,factoryArgs[4]),"Download receives the same icon wrapper and session as native save");
  int saveAdd=-1;for(int i=hook+2;i<body.size();i++){if(body.get(i) instanceof ReferenceInstruction x&&x.getReference() instanceof MethodReference r&&r.getName().equals("add")&&r.getParameterTypes().equals(List.of("Ljava/lang/Object;"))){saveAdd=i;break;}}
  check(saveAdd>hook&&followMoves(body,start,hook,args[1])==regs(body.get(saveAdd))[0],"Download is appended to the same list before bookmark/save");
  var create=method(button,"create");var cc=code(create);var ctor=calls(create).stream().filter(r->r.getName().equals("<init>")).toList();
  check(ctor.size()==1&&ctor.get(0).getParameterTypes().get(0).toString().equals("Landroid/widget/ImageView$ScaleType;"),"Download builds a native icon component using the APK-resolved constructor");
  check(call(create,button,"buttonId")&&call(create,"Lapp/morphe/extension/instagram/utils/IgStr;","str"),"Download has its own view identity and localized accessibility label");
  long handlers=calls(create).stream().filter(r->r.getParameterTypes().size()==2&&r.getParameterTypes().get(1).toString().equals("Lkotlin/jvm/functions/Function1;")).count();
  check(handlers==2,"Download replaces both save click and save long-click handlers");
  check(cc.stream().anyMatch(i->i instanceof WideLiteralInstruction l&&l.getWideLiteral()==2131240034),"Download component uses the target APK download drawable");
  for(String name:List.of("create","media","currentIndex","context"))for(var f:fields(method(button,name))){boolean found=false;var owner=before.get(f.getDefiningClass());if(owner!=null)for(Field nativeField:owner.getFields())if(nativeField.toString().equals(f.toString()))found=AccessFlags.PUBLIC.isSet(nativeField.getAccessFlags());check(found,"Feed bridge reads an existing public native field: "+f);}
  check(followMoves(body,start,hook,args[4])==followMoves(body,start,hook,factoryArgs[2])&&fields(method(button,"context")).stream().anyMatch(f->f.getType().equals("Landroid/content/Context;")),"Download uses the native row context rather than the last globally cached activity");
  check(fields(method(button,"media")).stream().anyMatch(f->f.getType().equals("Lcom/instagram/feed/media/Media;")),"Click resolves the media from the captured native row state");
  var position=code(method(button,"currentIndex"));int guard=-1;for(int i=0;i<position.size();i++)if(position.get(i).getOpcode()==Opcode.IF_EQZ)guard=i;
  check(guard>=0&&position.get(target(position,guard)).getOpcode().name().startsWith("CONST"),"Missing carousel state falls back to the first media item");
  check(call(method(button,"add"),"Lapp/morphe/extension/instagram/utils/Pref;","enableDownload"),"Disabling download also hides the added footer button");
  check(classes.get(button).getMethods().iterator().hasNext()&&classes.values().stream().filter(c->c.getType().startsWith(button.substring(0,button.length()-1))).flatMap(c->{var methods=new ArrayList<Method>();c.getMethods().forEach(methods::add);return methods.stream();}).anyMatch(m->call(m,ROOT+"DownloadUtils;","downloadPost")),"Footer click reuses the existing post download flow");
  String strings="Lapp/morphe/extension/instagram/utils/IgStr;";
  check(call(method(strings,"baseContext"),"Landroid/content/ContextWrapper;","getBaseContext")&&call(method(strings,"baseContext"),"Lapp/morphe/extension/shared/Utils;","getContext")&&!call(method(strings,"baseContext"),"Lapp/morphe/extension/shared/Utils;","getActivity"),"Piko labels bypass host activity language remapping through unwrapped package resources");
  String crash="Lapp/morphe/extension/instagram/utils/InstagramCrashHandler;";
  check(call(method(crash,"publish"),"Landroid/content/ContentResolver;","insert")&&call(method(crash,"publish"),"Landroid/content/ContentResolver;","openOutputStream"),"Crash reports use Android scoped storage");
  check(call(method(crash,"uncaughtException"),"Ljava/io/FileOutputStream;","<init>")&&call(method(crash,"<init>"),crash,"publish"),"A private crash copy is retained and retried on the next launch");
 }
 static void storyFilterChecks(Map<String,ClassDef> before) {
  String filter="Lapp/morphe/extension/instagram/patches/filter/story/FilterStory;";
  var parsers=new ArrayList<Method>();for(ClassDef c:classes.values())if(before.containsKey(c.getType()))for(Method m:c.getMethods())if(call(m,filter,"filter"))parsers.add(m);
  check(parsers.size()==1,"Story filtering intercepts exactly one native tray parser");Method parser=parsers.get(0);var body=code(parser);int hook=index(body,filter,"filter");
  int item=regs(body.get(hook))[0];
  check(hook>=3&&body.get(hook-1).getOpcode()==Opcode.IF_EQZ&&target(body,hook-1)==hook+2,"Null stories bypass filtering through the original null guard");
  check(body.get(hook-2).getOpcode()==Opcode.MOVE_RESULT_OBJECT&&((OneRegisterInstruction)body.get(hook-2)).getRegisterA()==item&&body.get(hook-3).getOpcode().name().startsWith("INVOKE_"),"Filter receives the parsed object result, never the field-name equality result");
  check(body.get(hook+1).getOpcode()==Opcode.MOVE_RESULT_OBJECT&&((OneRegisterInstruction)body.get(hook+1)).getRegisterA()==item&&body.get(hook+2).getOpcode()==Opcode.IF_EQZ&&((OneRegisterInstruction)body.get(hook+2)).getRegisterA()==item,"Filtered objects are checked by the original null guard");
  check(ref(body.get(hook+3)).getName().equals("add")&&regs(body.get(hook+3))[1]==item&&target(body,hook+2)==target(body,hook+4)&&target(body,hook+2)<hook-3,"Filtered-out items follow the same native loop continuation as null items");
  Method original=exact(before,parser);var originalCalls=calls(original).stream().map(Object::toString).toList();
  check(parser.getImplementation().getRegisterCount()==original.getImplementation().getRegisterCount()&&calls(parser).stream().filter(r->!r.getDefiningClass().equals(filter)).map(Object::toString).toList().equals(originalCalls),"Story filtering preserves the native parser frame and every native call");
 }
 static void storyCaptureChecks(Map<String,ClassDef> before) {
  String bridge="Lapp/morphe/extension/instagram/patches/story/StorySeenBridge;";
  for(String name:List.of("captureFromPromptProgress","captureFromStandardProgress","captureFromCompactProgress","captureFromHeaderBind")) {
   var entries=new ArrayList<Method>();
   for(ClassDef cls:classes.values())if(before.containsKey(cls.getType()))for(Method m:cls.getMethods())if(call(m,bridge,name))entries.add(m);
   check(entries.size()==1,"Exactly one native story capture entry: "+name);Method entry=entries.get(0);var body=code(entry);
   int hooks=(int)calls(entry).stream().filter(r->r.getDefiningClass().equals(bridge)&&r.getName().equals(name)).count();
   boolean header=name.equals("captureFromHeaderBind");
   check(hooks==(header?1+body.stream().filter(i->i.getOpcode()==Opcode.RETURN_VOID).count():1),"Story capture covers the intended entry and header exits: "+name);
   if(!header){int receiver=entry.getImplementation().getRegisterCount()-words(entry.getParameterTypes())-1;
    check(index(body,bridge,name)==0&&Arrays.equals(regs(body.get(0)),new int[]{receiver}),"Progress capture receives the current native holder: "+name);
   }
   Method nativeBridge=method(bridge,name+"Native");var nativeBody=code(nativeBridge);
   check(nativeBody.get(0).getOpcode()==Opcode.CHECK_CAST&&calls(nativeBridge).stream().noneMatch(r->r.getDefiningClass().startsWith("Ljava/lang/reflect/")),"Story capture resolves the native holder directly: "+name);
   var refs=fields(nativeBridge);check(refs.stream().anyMatch(f->f.getType().equals("Landroid/view/View;")),"Story capture reads a native root view: "+name);
   for(FieldReference f:refs){ClassDef owner=before.get(f.getDefiningClass());boolean exists=false;if(owner!=null)for(Field declared:owner.getFields())if(declared.toString().equals(f.toString()))exists=true;
    check(exists,"Story capture field exists in the original APK: "+f);
   }
  }
 }
 static void adapterChecks(Map<String,ClassDef> before) {
  for(ClassDef cls:classes.values())for(Method bridge:cls.getMethods()) {
   if(bridge.getName().equals("pikoSuggestedUsersForSession")) {
    var params=bridge.getParameterTypes();var body=code(bridge);
    check(AccessFlags.PUBLIC.isSet(bridge.getAccessFlags())&&AccessFlags.STATIC.isSet(bridge.getAccessFlags())&&params.size()==2&&params.get(0).equals("Lcom/instagram/common/session/UserSession;"),"Session classifier has a public direct-call boundary");
    var candidates=new ArrayList<Method>();for(Method m:before.get(cls.getType()).getMethods())if(!AccessFlags.STATIC.isSet(m.getAccessFlags())&&m.getParameterTypes().equals(List.of(params.get(1)))&&m.getReturnType().equals("Z"))candidates.add(m);
    check(candidates.size()==1,"Session classifier derives from one native predicate");var original=candidates.get(0);var nativeCode=code(original);
    check(body.size()==nativeCode.size()&&bridge.getImplementation().getRegisterCount()==original.getImplementation().getRegisterCount(),"Session classifier preserves its native frame and control flow");
    int changed=0;int firstParameter=bridge.getImplementation().getRegisterCount()-2;
    for(int i=0;i<body.size();i++){
     Instruction x=nativeCode.get(i),y=body.get(i);
     if(x.getOpcode()!=y.getOpcode()){
      changed++;check(x.getOpcode()==Opcode.IGET_OBJECT&&y.getOpcode()==Opcode.MOVE_OBJECT_FROM16&&x instanceof TwoRegisterInstruction&&y instanceof TwoRegisterInstruction,"Only the native session-field read changes");
      var f=(FieldReference)((ReferenceInstruction)x).getReference();var a=(TwoRegisterInstruction)x;var b=(TwoRegisterInstruction)y;
      check(f.getType().equals("Lcom/instagram/common/session/UserSession;")&&a.getRegisterA()==b.getRegisterA()&&a.getRegisterB()==firstParameter&&b.getRegisterB()==firstParameter,"Session argument replaces the original receiver field");
     }else{
      if(x instanceof ReferenceInstruction r)check(y instanceof ReferenceInstruction&&r.getReference().toString().equals(((ReferenceInstruction)y).getReference().toString()),"Classifier retains native reference at "+i);
      if(x instanceof OffsetInstruction o)check(y instanceof OffsetInstruction&&o.getCodeOffset()==((OffsetInstruction)y).getCodeOffset(),"Classifier retains branch offset at "+i);
     }
    }
    check(changed==1&&calls(bridge).stream().map(Object::toString).toList().equals(calls(original).stream().map(Object::toString).toList()),"Exactly one session read is adapted; all native calls remain");
   }
   if(bridge.getName().equals("pikoAggregateStoryMedia")){
    var refs=calls(bridge);var body=code(bridge);var params=bridge.getParameterTypes();
    check(AccessFlags.PUBLIC.isSet(bridge.getAccessFlags())&&AccessFlags.STATIC.isSet(bridge.getAccessFlags())&&params.size()==4&&params.get(1).equals("Lcom/instagram/common/session/UserSession;")&&params.get(2).equals(cls.getType())&&params.get(3).equals("Ljava/lang/String;"),"Typed story aggregate has a direct-call boundary");
    check(body.size()==9&&refs.size()==3&&body.get(8).getOpcode()==Opcode.RETURN_VOID&&bridge.getImplementation().getRegisterCount()==8,"Typed story aggregate has its bounded frame and constructor chain");
    check(refs.get(0).getName().equals("<init>")&&refs.get(0).getParameterTypes().equals(List.of("Ljava/util/List;","I","Ljava/lang/String;"))&&refs.get(1).getName().equals("<init>")&&refs.get(1).getParameterTypes().get(1).equals(params.get(0)),"Story wrapper retains the schema and converted media types");
    check(Arrays.equals(regs(body.get(6)),new int[]{1,0,4})&&Arrays.equals(regs(body.get(7)),new int[]{6,5,1,7}),"Story wrapper and aggregation preserve selected media, account, pending state and owner");
    check(before.containsKey(refs.get(2).getDefiningClass())&&refs.get(2).getDefiningClass().equals(cls.getType())&&resolve(refs.get(2))!=null,"Story aggregation directly reaches its native implementation");
   }
  }
 }
 static void entityBridgeChecks(Map<String,ClassDef> before) {
  String entity="Lapp/morphe/extension/instagram/entity/";
  for(String name:List.of("nativeHeight","nativeWidth","nativeCodec","nativeUrl","nativeMappings")) {
   String owner=entity+(name.equals("nativeMappings")?"UserFriendshipStatus;":"VideoData;");
   Method bridge=method(owner,name);var c=code(bridge);var refs=calls(bridge);
   check(AccessFlags.STATIC.isSet(bridge.getAccessFlags())&&bridge.getParameterTypes().equals(List.of("Ljava/lang/Object;")),"Entity bridge has an Object-only input: "+name);
   check(c.size()==5&&c.get(1).getOpcode()==Opcode.CHECK_CAST&&c.get(3).getOpcode()==Opcode.MOVE_RESULT_OBJECT&&c.get(4).getOpcode()==Opcode.RETURN_OBJECT,"Entity bridge directly returns the resolved native value: "+name);
   check(refs.size()==1,"Exactly one native entity call: "+name);var nativeCall=refs.get(0);
   String cast=((TypeReference)((ReferenceInstruction)c.get(1)).getReference()).getType();
   boolean mapper=name.equals("nativeMappings");
   check(c.get(2).getOpcode()==(mapper?Opcode.INVOKE_STATIC:Opcode.INVOKE_INTERFACE),"Native entity invocation kind: "+name);
   check(mapper?nativeCall.getParameterTypes().equals(List.of(cast)):nativeCall.getDefiningClass().equals(cast)&&nativeCall.getParameterTypes().isEmpty(),"Native entity cast and arguments agree: "+name);
   check(nativeCall.getReturnType().equals(bridge.getReturnType()),"Native entity return type agrees: "+name);
   ClassDef cls=before.get(nativeCall.getDefiningClass());
   check(cls!=null&&AccessFlags.PUBLIC.isSet(cls.getAccessFlags()),"Native entity owner is public: "+name);
   Method target=resolve(nativeCall);check(AccessFlags.PUBLIC.isSet(target.getAccessFlags()),"Native entity target exists and is public: "+name);
   check(!mapper||AccessFlags.STATIC.isSet(target.getAccessFlags()),"Native mapper is static: "+name);
  }
 }
 static final String ROOT="Lapp/morphe/extension/instagram/patches/download/";
 static final String B=ROOT+"NativeProfileBridge;", W=ROOT+"ProfileDownloader;", Q=ROOT+"InstagramDownloads;", N=ROOT+"TopDownloadNotice;";
 static List<FieldReference> fields(Method m) {
  var result=new ArrayList<FieldReference>();
  for(Instruction i:code(m))if(i instanceof ReferenceInstruction r&&r.getReference() instanceof FieldReference f)result.add(f);
  return result;
 }
 static Method anchored(ClassDef c,String text) {
  var found=new ArrayList<Method>();
  for(Method m:c.getMethods())if(code(m).stream().anyMatch(i->i instanceof ReferenceInstruction r&&r.getReference() instanceof StringReference s&&s.getString().equals(text)))found.add(m);
  check(found.size()==1,"Unique native anchor: "+text);return found.get(0);
 }
 static int words(List<? extends CharSequence> types) {int n=0;for(var t:types)n+=t.toString().equals("J")||t.toString().equals("D")?2:1;return n;}
 static void hookArgs(Method nativeMethod,String name,int... offsets) {
  var c=code(nativeMethod);int hook=index(c,W,name);int[] args=regs(c.get(hook));
  int p0=nativeMethod.getImplementation().getRegisterCount()-words(nativeMethod.getParameterTypes())-1;
  Map<Integer,Integer> origin=new HashMap<>();
  for(int n=p0;n<nativeMethod.getImplementation().getRegisterCount();n++)origin.put(n,n-p0);
  for(int n=0;n<hook;n++){
   Instruction i=c.get(n);
   if(i.getOpcode().toString().startsWith("MOVE")&&i instanceof TwoRegisterInstruction m)origin.put(m.getRegisterA(),origin.get(m.getRegisterB()));
   else if(i.getOpcode().setsRegister()&&i instanceof OneRegisterInstruction r)origin.remove(r.getRegisterA());
  }
  check(args.length==offsets.length,"Callback argument count: "+name);
  for(int n=0;n<args.length;n++)check(Objects.equals(origin.get(args[n]),offsets[n]),"Callback parameter preserved: "+name+" arg "+n);
 }
 static final String SUPPLIER="Ljava/util/function/BooleanSupplier;";
 record Gate(int query,int branch,int zero,int nonzero) {}
 static boolean reaches(List<Instruction> c,int from,int to,Set<Integer> blocked) {
  return reachable(c,from,blocked).contains(to);
 }
 static Set<Integer> reachable(List<Instruction> c,int from,Set<Integer> blocked) {
  Set<Integer> seen=new HashSet<>();ArrayDeque<Integer> pending=new ArrayDeque<>();pending.add(from);
  while(!pending.isEmpty()){
   int n=pending.remove();if(n<0||n>=c.size()||blocked.contains(n)||!seen.add(n))continue;
   String op=c.get(n).getOpcode().toString();
   if(op.startsWith("RETURN")||op.equals("THROW"))continue;
   if(op.startsWith("GOTO")){pending.add(target(c,n));continue;}
   if(op.startsWith("IF_"))pending.add(target(c,n));
   if(op.contains("SWITCH"))throw new AssertionError("Unexpected switch in download guard: "+n);
   pending.add(n+1);
  }
  return seen;
 }
 static boolean returns(List<Instruction> c,int from,Set<Integer> blocked) {
  return reachable(c,from,blocked).stream().anyMatch(n->c.get(n).getOpcode().toString().startsWith("RETURN"));
 }
 static Gate gate(List<Instruction> c,int query) {
  if(query+2>=c.size()||!(c.get(query+1) instanceof OneRegisterInstruction result)
    ||!c.get(query+1).getOpcode().toString().startsWith("MOVE_RESULT")
    ||!(c.get(query+2) instanceof OneRegisterInstruction branch)
    ||result.getRegisterA()!=branch.getRegisterA())throw new AssertionError("Guard result is not tested directly: "+query);
  int at=query+2,jump=target(c,at);
  return switch(c.get(at).getOpcode()){
   case IF_EQZ -> new Gate(query,at,jump,at+1);
   case IF_NEZ -> new Gate(query,at,at+1,jump);
   default -> throw new AssertionError("Guard must test null/boolean result: "+query);
  };
 }
 static boolean pausedGate(List<Instruction> c,Gate g,int from,int operation) {
  return !reaches(c,from,operation,Set.of(g.query()))
    &&reaches(c,g.zero(),operation,Set.of())&&!reaches(c,g.nonzero(),operation,Set.of())
    &&returns(c,g.nonzero(),Set.of());
 }
 static List<Gate> supplierGates(List<Instruction> c) {
  List<Gate> result=new ArrayList<>();
  for(int n=0;n<c.size();n++)if(c.get(n) instanceof ReferenceInstruction r&&r.getReference() instanceof MethodReference m
    &&m.getDefiningClass().equals(SUPPLIER)&&m.getName().equals("getAsBoolean"))result.add(gate(c,n));
  return result;
 }
 // Follow the actual Runnable register passed to Handler, then its calls, without lambda names.
 static List<Method> postedRunnables(Method parent,String postName) {
  Map<Integer,String> origins=new HashMap<>();List<Method> result=new ArrayList<>();
  for(Instruction i:code(parent)){
   if(i instanceof ReferenceInstruction r&&r.getReference() instanceof MethodReference m
     &&m.getDefiningClass().equals("Landroid/os/Handler;")&&m.getName().equals(postName)){
    int[] args=regs(i);String type=origins.get(args[1]);ClassDef cls=classes.get(type);
    if(cls!=null)for(Method run:cls.getMethods())if(run.getName().equals("run")&&run.getParameterTypes().isEmpty()&&run.getReturnType().equals("V"))result.add(run);
   }
   if(i.getOpcode()==Opcode.NEW_INSTANCE&&i instanceof ReferenceInstruction r&&r.getReference() instanceof TypeReference t)
    origins.put(((OneRegisterInstruction)i).getRegisterA(),t.getType());
   else if(i.getOpcode().toString().startsWith("MOVE_OBJECT")&&i instanceof TwoRegisterInstruction move){
    String source=origins.get(move.getRegisterB());if(source==null)origins.remove(move.getRegisterA());else origins.put(move.getRegisterA(),source);
   }else if(i.getOpcode().setsRegister()&&i.getOpcode()!=Opcode.CHECK_CAST&&i instanceof OneRegisterInstruction reg)origins.remove(reg.getRegisterA());
  }
  return result;
 }
 static boolean callsTransitively(Method from,Method wanted,Set<String> seen) {
  if(from.toString().equals(wanted.toString()))return true;
  if(!seen.add(from.toString()))return false;
  for(MethodReference call:calls(from))if(call.getDefiningClass().startsWith(ROOT)&&classes.containsKey(call.getDefiningClass()))
   if(callsTransitively(resolve(call),wanted,seen))return true;
  return false;
 }
 static boolean postedCallback(Method parent,String postName,Method callback) {
  return postedRunnables(parent,postName).stream().anyMatch(run->callsTransitively(run,callback,new HashSet<>()));
 }
 static List<Integer> activeWrites(Method m) {
  List<Integer> writes=new ArrayList<>();Set<Integer> active=new HashSet<>();var c=code(m);
  for(int n=0;n<c.size();n++){
   Instruction i=c.get(n);
   if(i instanceof ReferenceInstruction r&&r.getReference() instanceof MethodReference call&&call.getName().equals("put")
     &&call.getDefiningClass().equals("Ljava/util/Map;")&&active.contains(regs(i)[0]))writes.add(n);
   if(i.getOpcode()==Opcode.SGET_OBJECT&&i instanceof ReferenceInstruction r&&r.getReference() instanceof FieldReference f
     &&f.getDefiningClass().equals(N)&&f.getName().equals("ACTIVE"))active.add(((OneRegisterInstruction)i).getRegisterA());
   else if(i.getOpcode().toString().startsWith("MOVE_OBJECT")&&i instanceof TwoRegisterInstruction move){
    if(active.contains(move.getRegisterB()))active.add(move.getRegisterA());else active.remove(move.getRegisterA());
   }else if(i.getOpcode().setsRegister()&&i.getOpcode()!=Opcode.CHECK_CAST&&i instanceof OneRegisterInstruction reg)active.remove(reg.getRegisterA());
  }
  return writes;
 }
 static void downloadGuardChecks() {
  List<Method> pagers=new ArrayList<>(),progress=new ArrayList<>();
  for(ClassDef cls:classes.values())if(cls.getType().startsWith(ROOT))for(Method m:cls.getMethods()){
   if(call(m,B,"requestNext"))pagers.add(m);
   if(call(m,SUPPLIER,"getAsBoolean")&&!activeWrites(m).isEmpty())progress.add(m);
  }
  check(pagers.size()==1,"One extension callback dispatches native pagination");
  Method pager=pagers.get(0);var pc=code(pager);int request=index(pc,B,"requestNext"),cooldown=index(pc,Q,"cooldownReason");Gate limit=gate(pc,cooldown);
  check(postedCallback(method(W,"next"),"postDelayed",pager),"Cooldown gate runs in the Runnable actually posted by next");
  check(cooldown<request&&!reaches(pc,0,request,Set.of(cooldown))
    &&reaches(pc,limit.zero(),request,Set.of())&&!reaches(pc,limit.nonzero(),request,Set.of()),"Native requestNext is dominated by cooldownReason and only its null path can dispatch");
  Set<Integer> stops=new HashSet<>();for(int n=0;n<pc.size();n++)if(pc.get(n) instanceof ReferenceInstruction r&&r.getReference() instanceof MethodReference m&&m.getDefiningClass().equals(W)&&m.getName().equals("stop"))stops.add(n);
  check(stops.stream().anyMatch(n->reaches(pc,limit.nonzero(),n,Set.of()))&&returns(pc,limit.nonzero(),Set.of())
    &&!returns(pc,limit.nonzero(),stops),"A non-null cooldown stops the task before the delayed callback returns");

  check(progress.size()==1,"One emitted progress callback checks task liveness before writing ACTIVE");
  Method update=progress.get(0);var uc=code(update);var gates=supplierGates(uc);List<Integer> writes=activeWrites(update);
  check(postedCallback(method(N,"progress"),"post",update),"Liveness check runs inside the progress Runnable actually posted to MAIN");
  check(gates.stream().anyMatch(g->writes.stream().allMatch(write->g.query()<write&&!reaches(uc,0,write,Set.of(g.query()))
    &&reaches(uc,g.nonzero(),write,Set.of())&&!reaches(uc,g.zero(),write,Set.of()))&&returns(uc,g.zero(),Set.of())),"Inactive progress returns without resurrecting any ACTIVE card");

  Method requests=method(ROOT+"DownloadUtils;","profileRequests");var rc=code(requests);var pauses=supplierGates(rc);
  String media="Lapp/morphe/extension/instagram/entity/MediaData;";
  int count=index(rc,media,"getCarouselSize"),child=index(rc,media,"getMediaAt");
  check(requests.getParameterTypes().stream().anyMatch(t->t.toString().equals(SUPPLIER))
    &&pauses.stream().anyMatch(g->g.query()<count&&pausedGate(rc,g,0,count)),"Profile preparation checks its pause supplier before reading carousel metadata");
  check(pauses.stream().anyMatch(g->g.query()>count&&g.query()<child&&pausedGate(rc,g,0,child)
    &&!reaches(rc,child+1,child,Set.of(g.query()))),"Every carousel iteration checks pause before looking up the next child");
  List<Integer> metadata=List.of(index(rc,media,"getMediaLink"),index(rc,media,"getDownloadFilename"),index(rc,ROOT+"DownloadUtils;","buildMediaRequest"));
  check(pauses.stream().anyMatch(g->g.query()>child&&metadata.stream().allMatch(operation->g.query()<operation
    &&pausedGate(rc,g,child+1,operation))),"Pause after child lookup returns before reading child URL, filename or building its request");
 }
 static void switchStyleChecks(Map<String,ClassDef> before) {
  String owner="Lcom/instagram/igds/components/switchbutton/IgdsSwitch;";
  Method helper=method(owner,"pikoUseMaterialSwitchStyle");
  var hc=code(helper);
  check(hc.size()==4&&hc.get(3).getOpcode()==Opcode.RETURN,"Native switch style bridge has one read, policy call and return");
  FieldReference flag=(FieldReference)((ReferenceInstruction)hc.get(0)).getReference();
  ClassDef original=before.get(owner);
  Field definition=null;for(Field f:original.getFields())if(f.toString().equals(flag.toString()))definition=f;
  check(definition!=null&&flag.getType().equals("Z"),"Switch style bridge reads a real native boolean field");
  boolean isStatic=AccessFlags.STATIC.isSet(definition.getAccessFlags());
  check(hc.get(0).getOpcode()==(isStatic?Opcode.SGET_BOOLEAN:Opcode.IGET_BOOLEAN),"Switch style read preserves the target field storage shape");
  if(!isStatic)check(((TwoRegisterInstruction)hc.get(0)).getRegisterB()==helper.getImplementation().getRegisterCount()-1,"Instance style bridge reads the current switch receiver");
  check(ref(hc.get(1)).toString().equals("Lapp/morphe/extension/instagram/settings/preference/widgets/InstagramPreferenceStyle;->useMaterialSwitchStyle(Landroid/view/View;Z)Z")
    &&Arrays.equals(regs(hc.get(1)),new int[]{1,0})&&hc.get(2).getOpcode()==Opcode.MOVE_RESULT,"Only the existing preference-scoped style policy overrides the native flag");
  Set<String> paths=new HashSet<>();int replaced=0;
  for(Method originalMethod:original.getMethods()){
   int reads=0;for(Instruction i:code(originalMethod))if((i.getOpcode()==Opcode.SGET_BOOLEAN||i.getOpcode()==Opcode.IGET_BOOLEAN)
     &&i instanceof ReferenceInstruction r&&r.getReference().toString().equals(flag.toString()))reads++;
   if(reads==0)continue;
   Method patched=exact(classes,originalMethod);var pc=code(patched);int hooks=0;
   for(int n=0;n<pc.size();n++)if(pc.get(n) instanceof ReferenceInstruction r&&r.getReference() instanceof MethodReference m
      &&m.getDefiningClass().equals(owner)&&m.getName().equals(helper.getName())){
    int p0=patched.getImplementation().getRegisterCount()-words(patched.getParameterTypes())-1;
    check(Arrays.equals(regs(pc.get(n)),new int[]{p0})&&n+1<pc.size()&&pc.get(n+1).getOpcode()==Opcode.MOVE_RESULT,"Switch style hook keeps this and its scalar result: "+patched);
    hooks++;
   }
   check(hooks==reads,"Every native style read is replaced exactly once: "+patched);
   check(pc.stream().noneMatch(i->(i.getOpcode()==Opcode.SGET_BOOLEAN||i.getOpcode()==Opcode.IGET_BOOLEAN)
     &&i instanceof ReferenceInstruction r&&r.getReference().toString().equals(flag.toString())),"Original style reads cannot bypass the preference policy: "+patched);
   paths.add(originalMethod.getName());replaced+=reads;
  }
  check(replaced>0&&paths.containsAll(List.of("<init>","onDraw","setChecked")),"Style adaptation covers construction, drawing and checked-state animation");
 }
 public static void main(String[] a)throws Exception {
  classes.putAll(load(a[0]));var before=load(a[1]);
  switchStyleChecks(before);
  entityBridgeChecks(before);
  adapterChecks(before);
  storyCaptureChecks(before);
  storyFilterChecks(before);
  feedButtonChecks(before);
  var request=method(B,"requestNext");
  var nativeLoad=calls(request).stream().filter(r->!r.getDefiningClass().equals("Ljava/lang/Enum;")&&r.getReturnType().equals("V")).toList();
  check(nativeLoad.size()==1,"One native paging dispatch, no separate API client");
  Method originalLoad=exact(before,resolve(nativeLoad.get(0)));
  ClassDef originalHost=before.get(originalLoad.getDefiningClass());
  check(anchored(originalHost,"UserDetailFragment.loadMore").toString().equals(originalLoad.toString()),"Bridge dispatches original profile load-more method");
  check(code(request).stream().anyMatch(i->i instanceof ReferenceInstruction r&&r.getReference() instanceof StringReference s&&s.getString().equals("FULL_AUDIENCE_MEDIA_GRID")),"Pager targets the main Posts grid, excluding Tagged and subscription tabs");
  String host=originalLoad.getDefiningClass();
  for(String name:List.of("session","user","isMainGrid","plainMedia","mixedItems","mixedMedia","hasMore","cursor","isCached","requestNext","failureStatus")){
   var m=method(B,name);
   check(!call(m,B,"unresolved")&&code(m).stream().noneMatch(i->i.getOpcode()==Opcode.THROW),"Native bridge resolved: "+name);
   for(var r:calls(m))if(before.containsKey(r.getDefiningClass())){
    check(AccessFlags.PUBLIC.isSet(before.get(r.getDefiningClass()).getAccessFlags()),"Native owner public: "+r.getDefiningClass());
    Method target=resolve(r);check(AccessFlags.PUBLIC.isSet(target.getAccessFlags()),"Native method public: "+r);
   }
   for(var f:fields(m))if(before.containsKey(f.getDefiningClass())){
    check(AccessFlags.PUBLIC.isSet(before.get(f.getDefiningClass()).getAccessFlags()),"Native field owner public: "+f.getDefiningClass());
    boolean publicField=false;for(Field x:before.get(f.getDefiningClass()).getFields())if(x.toString().equals(f.toString()))publicField=AccessFlags.PUBLIC.isSet(x.getAccessFlags());
    check(publicField,"Native field exists and is public: "+f);
   }
  }
  var callbacks=new LinkedHashMap<String,String>();
  callbacks.put("FeedRequestCallback.onMediaFeedRequestSuccess","onPage");callbacks.put("FeedRequestCallback.onMediaFeedRequestFail","onFailure");
  callbacks.put("FeedRequestCallback.onMediaFeedRequestStart","onStart");callbacks.put("FeedRequestCallback.onMediaFeedRequestFinish","onFinish");
  for(var e:callbacks.entrySet()){
   Method original=anchored(originalHost,e.getKey());Method patched=method(host,original.getName());
   check(calls(patched).stream().filter(r->r.getDefiningClass().equals(W)&&r.getName().equals(e.getValue())).count()==1,"Exactly one native callback hook: "+e.getValue());
   if(e.getValue().equals("onPage"))hookArgs(patched,e.getValue(),0,2,3,4);
   else if(e.getValue().equals("onFailure"))hookArgs(patched,e.getValue(),0,1,2);
   else hookArgs(patched,e.getValue(),0,1);
  }
  for(String name:List.of("onResume","onPause","onDestroy"))hookArgs(method(host,name),name,0);
  Method fail=method(host,anchored(originalHost,"FeedRequestCallback.onMediaFeedRequestFail").getName());
  var fc=code(fail);int guard=index(fc,W,"hasTask");
  check(fc.get(guard+1).getOpcode()==Opcode.MOVE_RESULT&&fc.get(guard+2).getOpcode()==Opcode.IF_NEZ,"Duplicate error toast has a task-owned gate");
  int destination=target(fc,guard+2);var skipped=fc.subList(guard+3,destination);
  check(skipped.size()==1&&ref(skipped.get(0)).getReturnType().equals("Landroid/widget/Toast;"),"Only the native failure Toast call is bypassed during a bulk download");
  Method download=method(ROOT+"DownloadUtils;","downloadMedia");
  check(call(download,Q,"download")&&!call(download,"Lapp/morphe/extension/crimera/legacydownloader/MediaDownloader;","enqueue"),"Single posts and carousels use the Instagram serial queue");
  check(call(method(ROOT+"DownloadUtils;","enqueueDownload"),Q,"download"),"Avatars, voice messages and other URL downloads share top feedback");
  downloadGuardChecks();
  boolean overlay=false,toast=false;
  for(ClassDef cls:classes.values())if(cls.getType().startsWith(ROOT+"TopDownloadNotice"))for(Method m:cls.getMethods())for(var r:calls(m)){
   if(r.getDefiningClass().equals("Landroid/view/WindowManager;"))overlay=true;
   if(r.getDefiningClass().equals("Landroid/widget/Toast;"))toast=true;
  }
  check(!overlay&&!toast,"Top notices are activity content; no overlay permission or bottom Toast");
  var text=method(ROOT+"TopDownloadNotice$Card;","text");
  check(call(text,"Landroid/widget/TextView;","setEllipsize")&&code(text).stream().anyMatch(i->i instanceof WideLiteralInstruction l&&l.getWideLiteral()==Integer.MAX_VALUE),"Full notice text wraps without ellipsis or line limit");
  var card=method(ROOT+"TopDownloadNotice$Card;","<init>");
  check(call(card,"Landroid/widget/ProgressBar;","<init>"),"Top notice contains a real progress bar");
  for(ClassDef cls:classes.values())if(cls.getType().startsWith(ROOT))for(Method m:cls.getMethods())for(Instruction i:code(m))if(i instanceof FiveRegisterInstruction||i instanceof RegisterRangeInstruction)for(int r:regs(i))if(r>=m.getImplementation().getRegisterCount())throw new AssertionError("Register overflow: "+m);
  check(true,"Download hooks and extension invokes fit emitted register frames");
  var expected=strings(Path.of(a[3]).resolve("patches/src/main/resources/addresources/values-zh-rCN/instagram/strings.xml"));
  var english=strings(Path.of(a[3]).resolve("patches/src/main/resources/addresources/values/instagram/strings.xml"));
  check(expected.keySet().containsAll(english.keySet()),"Every Instagram settings string has a Simplified Chinese entry");
  try(ApkModule apk=ApkModule.loadApkFile(new File(a[0]))){
   apk.setLoadDefaultFramework(false);check(apk.getPackageName().equals("com.instagram.android"),"Original Instagram package preserved");
   var table=apk.getTableBlock();
   for(String key:expected.keySet()){
    boolean found=false;var entries=table.getEntries(null,"string",key);
    String wanted=expected.get(key).replace("\\\"","\"").replace("\\n","\n");
    while(entries.hasNext()){var entry=entries.next();if("zh".equals(entry.getResConfig().getLanguage())&&"CN".equals(entry.getResConfig().getRegion())&&wanted.equals(entry.getValueAsString()))found=true;}
    check(found,"Compiled Chinese Instagram text matches source: "+key);
   }
  }
  Map<String,Object>report=new LinkedHashMap<>();report.put("apk",a[0]);report.put("native_pager",originalLoad.toString());report.put("checks",checks);report.put("passed",true);
  Files.writeString(Path.of(a[2]),json(report)+"\n");System.out.println("Passed "+checks.size()+" Instagram download checks");
 }
}
