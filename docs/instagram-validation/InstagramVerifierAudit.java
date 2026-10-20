import com.android.tools.smali.dexlib2.Opcode;
import com.android.tools.smali.dexlib2.iface.*;
import com.android.tools.smali.dexlib2.iface.instruction.*;
import java.nio.file.*;
import java.util.*;

/** Structural checks for the startup VerifyError reported on IG 451 / Android 16. */
public final class InstagramVerifierAudit extends FixedRefreshStaticReview {
 static final Set<Opcode> RESULTS=Set.of(Opcode.MOVE_RESULT,Opcode.MOVE_RESULT_OBJECT,Opcode.MOVE_RESULT_WIDE);
 public static void main(String[] args)throws Exception {
  var snapshot=load(args[0]);var failures=new ArrayList<String>();int methods=0,branches=0,results=0;
  for(ClassDef cls:snapshot.values())for(Method method:cls.getMethods()) {
   if(method.getImplementation()==null)continue;methods++;
   var code=new ArrayList<Instruction>();method.getImplementation().getInstructions().forEach(code::add);
   Map<Integer,Integer> addresses=new HashMap<>();int[] starts=new int[code.size()];int address=0;
   for(int n=0;n<code.size();n++){starts[n]=address;addresses.put(address,n);address+=code.get(n).getCodeUnits();}
   for(int n=0;n<code.size();n++){
    Instruction instruction=code.get(n);Opcode opcode=instruction.getOpcode();String name=opcode.name();
    if(RESULTS.contains(opcode)){
     results++;String previous=n==0?"":code.get(n-1).getOpcode().name();
     if(!previous.startsWith("INVOKE_")&&!previous.startsWith("FILLED_NEW_ARRAY"))failures.add(method+" @"+Integer.toHexString(starts[n])+": orphan "+opcode);
    }
    List<Integer> destinations=new ArrayList<>();
    if(instruction instanceof OffsetInstruction branch){
     int destination=starts[n]+branch.getCodeOffset();
     if(name.startsWith("IF_")||name.startsWith("GOTO"))destinations.add(destination);
     else if(opcode==Opcode.PACKED_SWITCH||opcode==Opcode.SPARSE_SWITCH){
      Integer payload=addresses.get(destination);
      if(payload==null||!(code.get(payload) instanceof SwitchPayload))failures.add(method+": missing switch payload");
      else for(SwitchElement element:((SwitchPayload)code.get(payload)).getSwitchElements())destinations.add(starts[n]+element.getOffset());
     }
    }
    for(int destination:destinations){branches++;Integer target=addresses.get(destination);
     if(target==null)failures.add(method+" @"+Integer.toHexString(starts[n])+": branch outside an instruction boundary");
     else if(RESULTS.contains(code.get(target).getOpcode())||code.get(target).getOpcode()==Opcode.MOVE_EXCEPTION)
      failures.add(method+" @"+Integer.toHexString(starts[n])+": branch targets "+code.get(target).getOpcode()+" @"+Integer.toHexString(destination));
    }
   }
  }
  Map<String,Object> report=new LinkedHashMap<>();report.put("apk",Path.of(args[0]).getFileName().toString());report.put("classes",snapshot.size());report.put("methods",methods);report.put("branches",branches);report.put("result_instructions",results);report.put("issues",failures);report.put("passed",failures.isEmpty());
  Files.writeString(Path.of(args[1]),json(report)+"\n");
  System.out.println("Methods="+methods+" branches="+branches+" results="+results+" issues="+failures.size());
  failures.stream().limit(25).forEach(System.out::println);
  if(!failures.isEmpty())System.exit(1);
 }
}
