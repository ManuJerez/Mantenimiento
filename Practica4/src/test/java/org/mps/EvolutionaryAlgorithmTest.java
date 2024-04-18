package org.mps;

import org.junit.jupiter.api.*;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.OnePointCrossover;
import org.mps.mutation.SwapMutation;
import org.mps.selection.TournamentSelection;

import static org.junit.jupiter.api.Assertions.*;

public class EvolutionaryAlgorithmTest {

    private TournamentSelection tournamentSelection;
    private SwapMutation swapMutation;
    private OnePointCrossover onePointCrossover;
    EvolutionaryAlgorithm evolutionaryAlgorithm;
    int[][] population;

    @BeforeEach()
    void setup() throws EvolutionaryAlgorithmException {
        tournamentSelection = new TournamentSelection(5);
        swapMutation = new SwapMutation();
        onePointCrossover = new OnePointCrossover();

        evolutionaryAlgorithm = new EvolutionaryAlgorithm(tournamentSelection, swapMutation, onePointCrossover);
    }

    @Nested
    @DisplayName("Constructores")
    class construct{
        @Test
        @DisplayName("Constructor EvolutionaryAlgorithm con operador de seleccion nulo lanza excepcion")
        void evolutionaryAlgorithmConstruct_WithNullSelectionOperator_ThrowException(){
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new EvolutionaryAlgorithm(null, swapMutation, onePointCrossover);
            });
        }
        @Test
        @DisplayName("Constructor EvolutionaryAlgorithm con operador de mutacion nulo lanza excepcion")
        void evolutionaryAlgorithmConstruct_WithNullMutationOperator_ThrowException(){
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new EvolutionaryAlgorithm(tournamentSelection, null, onePointCrossover);
            });
        }
        @Test
        @DisplayName("Constructor EvolutionaryAlgorithm con operador de cruce nulo lanza excepcion")
        void evolutionaryAlgorithmConstruct_WithNullCrossoverOperator_ThrowException(){
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new EvolutionaryAlgorithm(tournamentSelection, swapMutation, null);
            });
        }
        @Test
        @DisplayName("Constructor TournamentSelection con tamanyo menor o igual a cero lanza excepcion")
        void tournamentSelectionConstruct_WithSizeZero_ThrowException(){
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new TournamentSelection(0);
            });
        }
    }

    @Nested
    @DisplayName("Con poblacion correcta")
    class correctPopulation{
        @BeforeEach()
        void setupCorrectPopulation(){
            //population = new int[6][6];
            population = new int[5][5];
            for(int i = 0; i < population.length; i++){
                for(int j = 0; j < population[0].length; j++){
                    population[i][j] = (int) (Math.random()*10);
                }
            }
        }

        @Test
        @DisplayName("Optimizar devuelve poblacion mismo tamanyo")
        void optimize_CorrectPopulation_ReturnPopulationSameSize() throws EvolutionaryAlgorithmException {
            int[][] optimizedPopulation = evolutionaryAlgorithm.optimize(population);

            assertNotNull(optimizedPopulation);
            assertEquals(optimizedPopulation.length, population.length);
        }

        @Test
        @DisplayName("Operador de cruce devuelve nuevas soluciones distintas")
        void crossoverOperator_CorrectPopulation_ReturnNewSolution() throws EvolutionaryAlgorithmException {
            CrossoverOperator crossoverOperator = evolutionaryAlgorithm.getCrossoverOperator();

            int[][] crossoveredPopulation = crossoverOperator.crossover(population[0], population[1]);
            boolean sameSolutions = true;

            for(int i = 0; i < 2; i++){
                for(int j = 0; j < population[0].length; j++){
                    if (population[i][j] != crossoveredPopulation[i][j]) {
                        sameSolutions = false;
                        break;
                    }
                }
            }

            assertFalse(sameSolutions);
        }

        @Test
        @DisplayName("Operador de cruce con padre 1 nulo lanza excepcion")
        void crossoverOperator_NullParent1_ThrowException(){
            CrossoverOperator crossoverOperator = evolutionaryAlgorithm.getCrossoverOperator();
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                crossoverOperator.crossover(null, population[1]);
            });
        }

        @Test
        @DisplayName("Operador de cruce con padre 2 nulo lanza excepcion")
        void crossoverOperator_NullParent2_ThrowException(){
            CrossoverOperator crossoverOperator = evolutionaryAlgorithm.getCrossoverOperator();
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                crossoverOperator.crossover(population[0], null);
            });
        }

        @Test
        @DisplayName("Operador de cruce con padres de diferente tamanyo lanza excepcion")
        void crossoverOperator_DiffSizeParents_ThrowException(){
            CrossoverOperator crossoverOperator = evolutionaryAlgorithm.getCrossoverOperator();
            int[] parent1 = new int[5];
            int[] parent2 = new int[4];

            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                crossoverOperator.crossover(parent1, parent2);
            });
        }

    }
    @Nested
    @DisplayName("Con poblacion nula")
    class nullPopulation{
        @Test
        @DisplayName("Optimizar lanza excepcion")
        void optimize_NullPopulation_ThrowException(){
            assertThrows(EvolutionaryAlgorithmException.class, () ->{
               evolutionaryAlgorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Optimizar con primera solucion nula lanza excepcion")
        void optimize_FirstSolutionNull_ThrowException(){
            population = new int[5][5];
            population[0] = null;
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                evolutionaryAlgorithm.optimize(population);
            });
        }
    }

    @Nested
    @DisplayName("Con poblacion vacia")
    class emptyPopulation{
        int[][] totalEmptyPopulation;

        @BeforeEach
        void setupEmptyPopulation(){
            totalEmptyPopulation = new int[0][0];
            population = new int[5][0];
        }

        @Test
        @DisplayName("Optimizar lanza excepcion")
        void optmize_EmptyPopulation_ThrowException(){
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                evolutionaryAlgorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Optimizar poblacion sin soluciones lanza excepcion")
        void optimize_TotalEmptyPopulation_ThrowException(){
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                evolutionaryAlgorithm.optimize(totalEmptyPopulation);
            });
        }

        @Test
        @DisplayName("Operador de cruce lanza excepcion")
        void crossoverOperator_EmptyPopulation_ThrowException(){
            CrossoverOperator crossoverOperator = evolutionaryAlgorithm.getCrossoverOperator();
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                crossoverOperator.crossover(population[0], population[1]);
            });
        }
    }
}






