package Core;

public class KECCAK {
    /**
     * <h1>KECCAK-f [b]</h1>
     * <p>The family of seven permutations originally specified in [8] as the underlying function for KECCAK. The set of values for the width b of the permutations is {25, 50, 100, 200, 400, 800, 1600}.</p>
     * <p>For SHA-3, b=1600, which means the state is a long[25] array.</p>
     * @param state The 1600-bit state array (long[25]) to be permuted.
     */
    public static void KECCAK_f(long[] state) {
         KECCAK_p(state, 24);
    }

    /**
     * <h1>Algorithm 7: KECCAK-p[b, n_r](S)</h1>
     * <p>The generalization of the KECCAK-f[p] permutations that is defined in this Standard by converting the number of rounds n_r to an input parameter.</p>
     * Steps :
     * <p>1. Convert S into a state array, A, as described in Sec. 3.1.2.</p>
     * <p>2. For i_r from 12+2l-n_r to 12+2l-1, let A = Rnd(A, i_r)</p>
     * <p>3. Convert A into a string S' of length b, as described in Sec. 3.1.3.</p>
     * <p>4. Return S'.</p>
     * @param state The state array (long[25]) to be permuted.
     * @param rounds The number of rounds.
     */
    public static void KECCAK_p(long[] state, int rounds) {
        for (int i = 0; i < rounds; i++) {
            Rnd(state, i);
        }
    }
    
    private static void Rnd(long[] state, int roundIndex) {
        StepMappings.theta(state);
        StepMappings.rho(state);
        StepMappings.pi(state);
        StepMappings.chi(state);
        StepMappings.iota(state, roundIndex);
    }
}
