
import java.util.Arrays;
import java.util.List;

/**
 * Created by pyshankov on 14.05.2016.
 */
public class LinearFeedbackShiftRegister {

    private final Polynom characteristicPolynon;
    private final int[] currentState;
    private final int[] polynomFunc;

    LinearFeedbackShiftRegister(Polynom p){
        characteristicPolynon = p;
        currentState = new int[p.getPolymomCoefficient().length-1];
        //first state
        currentState[currentState.length-1]=1;

        List<Integer> func = Polynom.getIndexOfOne(characteristicPolynon);
        polynomFunc = new int[func.size()];
        for (int i = 0 ; i < polynomFunc.length ; i++){
            polynomFunc[i]=func.get(i);
        }
    }

    LinearFeedbackShiftRegister(Polynom p, int[] firstState){
        characteristicPolynon = p;
        currentState = new int[p.getPolymomCoefficient().length-1];

        //first state
        for (int i = 0 ; i < firstState.length ; i++) {
            currentState[i] = firstState[i];
        }

        List<Integer> func = Polynom.getIndexOfOne(characteristicPolynon);
        polynomFunc = new int[func.size()];
        for (int i = 0 ; i < polynomFunc.length ; i++){
            polynomFunc[i]=func.get(i);
        }
    }

    private int generateElem(int[] src) {
        int res=0;
        int length = src.length-1;
        for (int i = 0 ; i < polynomFunc.length ; i++){
            res =res^src[length-polynomFunc[i]];
        }
        return res;
    }

    public int generateNextSequence(){
        int res = currentState[0];
        int newElem = generateElem(currentState);
        for (int i = 0 ; i < currentState.length ; i ++){
            if(i+1 == currentState.length ) break;
            currentState[i]=currentState[i+1];
        }
        currentState[currentState.length-1] = newElem;
        return res;
    }

    public static int genaratorGeffe(LinearFeedbackShiftRegister l1,
                                     LinearFeedbackShiftRegister l2,
                                     LinearFeedbackShiftRegister l3){

       int x = l1.generateNextSequence();
       int y = l2.generateNextSequence();
       int s = l3.generateNextSequence();
        return (s&x)^((1^s)&y);
    }

    public static void main(String[] args) {

        String zi = "00011000010101111000000110101001101100001111100100011011010011000001001010110110000011010000101100110101011001011001001100010100010100010100011111110110111010000101010011001010101000010101011111110100011000100100110111110100000011000001101111000110101000111010000001010010100000100100100111110111100111110100100101010011110100101100001011110000110001100100100100011100001110110010010001000011110100100000000100100001001111111101111101000000110100011110111001101110010000010001110000111000111011100101000111101011111111011111110100111000110101001000111010100001111111011111000011000011110011011110101010101101110101111001011111100100010100100000010001011110100010010010100101101110110111110100010110100001001101111010101010111101110111001000001100101100001101110001011000010010100010110001000100000101011001000010001111001010111111000101000011110001110010010011111110110001111101110000001111111001101111000001011001111001111001110011101010011111101011001011010011111110110010000001000010100010100001000001010100001010111011011111100000000101001001100000110001101101010001010100010101011110001000000011101000011110011000110101000101001111101100101100001010011111000001100101101110001100010011011110110010111100110110000111000000000110001111010111000011101010001100001000111111110100011001110010110110100001001011001000100100011011010100000001001111100001000110111011100001011000110110100001110111010001101111011000011110101100101110001100100110100001101000000111101010101110100100011000011010111010110111100110000001101111011111010101101011010100111101100011110000111010011001001101111000011011011011010010110011011101010010000110000011111010000001100000001000110110000001101000111001000001110101010101101100111110100001010011111001000010011111001101111000001010100100011111010100111101111111100100010010111111100100010000101111101000000010100010010011100100011001111111111001100011110011000001000000011100001110111100101001100011111010010100100101110000110101000100101111110000111010000111110001000100000111000000110001111010100010010110100001001110";

        int[] z = new int[zi.length()];
        for (int i = 0 ; i < z.length ; i++){
            z[i] = Integer.parseInt(zi.substring(i,i+1));
        }
        int[] l1Px = {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,1,1};
        int[] l2Px = {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1};
        int[] l3Px = {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,1,1};

        int[] test = {1,1,0,1};
//        Polynom l1px = new Polynom(test);

        Polynom l1px = new Polynom(l1Px);
        Polynom l2px = new Polynom(l2Px);
        Polynom l3px = new Polynom(l3Px);

        LinearFeedbackShiftRegister L1 = new LinearFeedbackShiftRegister(l1px);
        LinearFeedbackShiftRegister L2 = new LinearFeedbackShiftRegister(l2px);
        LinearFeedbackShiftRegister L3 = new LinearFeedbackShiftRegister(l3px);


        System.out.println(CryptoAnalysis.N(2.326,6.0093));
        System.out.println(CryptoAnalysis.C(6.0093,(int) CryptoAnalysis.N(2.326,6.0093)));
//        System.out.println(R(L1,z));


//
//        int[] array = new int[L1.currentState.length];
//
//        System.arraycopy(L1.currentState,0,array,0,array.length);
//
//        System.out.println(Arrays.toString(L1.polynomFunc));
//        int elem =(int) Math.pow(2d,L1.characteristicPolynon.getPolymomCoefficient().length-1);
//
//        for (int i = 0 ; i < elem ; i++ ) {
//            if(Arrays.equals(array,L1.currentState)) System.out.println(i);
//            System.out.println(Arrays.toString(L1.currentState));
//            L1.generateNextSequence();
//        }


//        for(int i = 0 ; i < 2048 ; i++)
//        System.out.println(genaratorGeffe(L1,L2,L3));


    }
}
