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
    }

    @Nested
    @DisplayName("Con poblacion nula")
    class nullPopulation{
        @BeforeEach()
        void setupNullPopulation(){
            population = null;
        }

        @Test
        @DisplayName("Optimizar lanza excepcion")
        void optimize_NullPopulation_ThrowException(){
            assertThrows(EvolutionaryAlgorithmException.class, () ->{
               evolutionaryAlgorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Operador de cruce lanza excepcion")
        void crossoverOperator_NullPopulation_ThrowException(){
            CrossoverOperator crossoverOperator = evolutionaryAlgorithm.getCrossoverOperator();
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
               crossoverOperator.crossover(null, null);
            });
        }
    }
}






