

/**
 * Created by pyshankov on 14.05.2016.
 */
public class LinearFeedbackShiftRegister {
    //defines an polynom for function or multiplicative mask
    private final long characteristicPolynon;

    private final long firstState;

    //defines current state of register
    private long currentState;

    //defines a function for generating elements
    private final long multiplicativeMask;

    //defines a length of register;
    private final int registerLength;


    LinearFeedbackShiftRegister(long characteristicPolynon,long firstState){
        this.characteristicPolynon=characteristicPolynon;
        int n = Long.numberOfLeadingZeros(this.characteristicPolynon) + 1;
        long identityMask = (0xFFFF_FFFF >>> n);
        this.registerLength = 64 - n ;
        this.multiplicativeMask = characteristicPolynon & identityMask;
        this.firstState= firstState & identityMask;
        this.currentState = this.firstState;
    }

    public long generateNext(){
            long outputBit = currentState & 1;
            long nextBit = Long.bitCount(currentState & multiplicativeMask) & 1 ;
            currentState = ( currentState >>> 1 ) | (nextBit << (registerLength - 1));
            return outputBit;
    }

    public String getCurrentState() {
        String binary = Long.toBinaryString(currentState & (0xFFFF_FFFF >>> (64 - registerLength)));
        int needZeros = registerLength - binary.length();
        StringBuilder builder = new StringBuilder(registerLength);
        while (needZeros-- > 0) {
            builder.append("0");
        }
        builder.append(binary);
        return builder.toString();
    }

    public static class GeffeGen {

        private LinearFeedbackShiftRegister l1;
        private LinearFeedbackShiftRegister l2;
        private LinearFeedbackShiftRegister l3;

        public GeffeGen(LinearFeedbackShiftRegister l1, LinearFeedbackShiftRegister l2, LinearFeedbackShiftRegister l3) {
            this.l1 = l1;
            this.l2 = l2;
            this.l3 = l3;
        }

        public long getNext(){
            long x = l1.generateNext();
            long y = l2.generateNext();
            long s = l3.generateNext();
            return (x & s) ^ ((1 ^ s) & y) ;
        }

    }


    public static void main(String[] args) {


        long test = 0b1101;

        long l1Px = 0b1000000000000000000000001010011l;
        long l2Px = 0b10000000000000000000000000001001l;
        long l3Px = 0b100000000000000000000000010101111l;

        System.out.println(l3Px);

        LinearFeedbackShiftRegister l1 = new LinearFeedbackShiftRegister(l1Px, 1);
        LinearFeedbackShiftRegister l2 = new LinearFeedbackShiftRegister(l2Px, 1);
        LinearFeedbackShiftRegister l3 = new LinearFeedbackShiftRegister(l3Px, 1);

        System.out.println(l3.getCurrentState());



        GeffeGen geffeGen = new GeffeGen(l1,l2,l3);

        for ( int i = 0 ; i < 2048 ; i++ ){
            System.out.println(geffeGen.getNext());
            System.out.println(l3.getCurrentState());
        }



    }

}
