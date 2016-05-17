import java.util.List;
import java.util.concurrent.*;

/**
 * Created by pyshankov on 17.05.2016.
 */
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1);


        long l1Px = 0b1000000000000000000000001010011l;
        long l2Px = 0b10000000000000000000000000001001l;
        long l3Px = 0b100000000000000000000000010101111l;

//        int l1Px = 0b100000000101;
//        int l2Px = 0b1000011011;
//        int l3Px = 0b10000001001;

//         Callable<List<Long>> L1StatesTask = ()-> CryptoAnalysis.firstStates(l1Px,CryptoAnalysis.zi,0.01);
//         Callable<List<Long>> L2StatesTask = ()-> CryptoAnalysis.firstStates(l2Px,CryptoAnalysis.zi,0.01);
//
//            List<Long> l1List = executorService.submit(L1StatesTask).get();
//            List<Long> l2List = executorService.submit(L2StatesTask).get();

        CryptoAnalysis.breakGeffe(l1Px,l2Px,l3Px,CryptoAnalysis.zi,0.01);


        LinearFeedbackShiftRegister p1 = new LinearFeedbackShiftRegister(l1Px,1146);
        LinearFeedbackShiftRegister p2 = new LinearFeedbackShiftRegister(l1Px,5);
        LinearFeedbackShiftRegister p3 = new LinearFeedbackShiftRegister(l1Px,855);

//        LinearFeedbackShiftRegister.GeffeGen gen = new LinearFeedbackShiftRegister.GeffeGen(p1,p2,p3);
//        boolean b = true;
//        long[]z1 = CryptoAnalysis.fromStringToMass(CryptoAnalysis.zi);
//        for (int i = 0 ; i < CryptoAnalysis.zi.length() ; i++){
//         b = (gen.getNext() == z1[i]);
//        }
//        System.out.println(b);




//        executorService.shutdown();
    }
}
