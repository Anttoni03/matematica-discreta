import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * Cada tema té el mateix pes, i l'avaluació consistirà en:
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - La neteja del codi (pensau-ho com faltes d'ortografia). L'estàndar que heu de seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . No és
 *   necessari seguir-la estrictament, però ens basarem en ella per jutjar si qualcuna se'n desvia
 *   molt.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: FRAU GORDIOLA, Toni
 * - Nom 2: MOLNAR ARDELEAN, Denis
 * - Nom 3: SANTANER CASTRO, Jose Benito
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital abans de la data que se us hagui
 * comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més fàcilment
 * les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat, assegurau-vos
 * que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
   * l'univers, podeu fer-ho com `p.test(x)`, té com resultat un booleà. Els predicats de dues
   * variables són de tipus `BiPredicate<Integer, Integer>` i similarment s'avaluen com
   * `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
   * petit com per utilitzar la força bruta)
   */
  static class Tema1 {
    /*
     * És cert que ∀x,y. P(x,y) -> Q(x) ^ R(y) ?
     */
    static boolean exercici1(
        int[] universe,
        BiPredicate<Integer, Integer> p,
        Predicate<Integer> q,
        Predicate<Integer> r) {

      for (int x : universe) {
           
          for (int y : universe) {
               
              if (p.test(x, y)) {
                   
                  if (!((q.test(x))&&(r.test(y)))) {
                     
                      return false;
                  }
              }
          }
      }
      return true;
    }

    /*
     * És cert que ∃!x. ∀y. Q(y) -> P(x) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
      boolean solucionHaSidoEncontrada = false;
      boolean solucionEsValida;
        
      for (int x : universe) {
           
        solucionEsValida = true;
           
        for (int y : universe) {
               
          if ((q.test(y))&&(!p.test(x))) {
                  
            solucionEsValida = false;
          }
        }
        if (solucionEsValida) {
                
          if (solucionHaSidoEncontrada) return false;
          solucionHaSidoEncontrada = true;
        }
      }
      return solucionHaSidoEncontrada;
    }

    /*
     * És cert que ¬(∃x. ∀y. y ⊆ x) ?
     *
     * Observau que els membres de l'univers són arrays, tractau-los com conjunts i podeu suposar
     * que cada un d'ells està ordenat de menor a major.
     */
    static boolean exercici3(int[][] universe) {
      boolean casoNoValido;
        
      for (int [] x : universe) {
            
        casoNoValido = false;
            
        for (int [] y : universe) {
                
          if (Tema2.interseccion(x, y).length != y.length) {

            casoNoValido = true;
          }
        }
        if (!casoNoValido) {
                
          return false;
        }
      }
      return true;
    }

    /*
     * És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?
     */
    static boolean exercici4(int[] universe, int n) {
      boolean casoVerificado;
        
      for (int x : universe) {
            
        casoVerificado = false;
            
        for (int y : universe) {
                
          if ((x*y)%n == 1) {
                    
            if (!casoVerificado) {
                        
              casoVerificado = true;
            }else {
                        
              return false;
            }
          }
        }
        if (!casoVerificado) {
                
          return false;
        }
      }
      return true;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // ∀x,y. P(x,y) -> Q(x) ^ R(y)

      assertThat(
          exercici1(
              new int[] { 2, 3, 5, 6 },
              (x, y) -> x * y <= 4,
              x -> x <= 3,
              x -> x <= 3
          )
      );

      assertThat(
          !exercici1(
              new int[] { -2, -1, 0, 1, 2, 3 },
              (x, y) -> x * y >= 0,
              x -> x >= 0,
              x -> x >= 0
          )
      );

      // Exercici 2
      // ∃!x. ∀y. Q(y) -> P(x) ?

      assertThat(
          exercici2(
              new int[] { -1, 1, 2, 3, 4 },
              x -> x < 0,
              x -> true
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3, 4, 5, 6 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0  // x és múltiple de 4
          )
      );

      // Exercici 3
      // ¬(∃x. ∀y. y ⊆ x) ?

      assertThat(
          exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {} })
      );

      assertThat(
          !exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {}, {0, 1, 2, 3} })
      );

      // Exercici 4
      // És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?

      assertThat(
          exercici4(
              new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
              11
          )
      );

      assertThat(
          !exercici4(
              new int[] { 0, 5, 7 },
              13
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * De la mateixa manera que al Tema 1, per senzillesa tractarem els conjunts com arrays (sense
   * elements repetits). Per tant, un conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant int[] a,
   * int[] b, i un objecte de tipus Function<Integer, Integer> que podeu avaluar com f.apply(x) (on
   * x és un enter d'a i el resultat f.apply(x) és un enter de b).
   */
  static class Tema2 {
    
    static int [] interseccion(int [] a, int [] b) {     
      ArrayList<Integer> elementosComunes = new ArrayList<>();
      int [] out;
      
      for (int ea : a) {
          
        for (int eb : b) {
              
          if (ea == eb) {
                  
            elementosComunes.add(ea);
          }
        }
      }
      out = new int[elementosComunes.size()];
      
      for (int i = 0; i < elementosComunes.size(); i++) {
          
        out[i] = elementosComunes.get(i);
      }
      return out;
    }

    static int [] union(int [] a, int [] b){
      ArrayList<Integer> elementosEncontrados = new ArrayList<>();
      int [] out; int aux;
            
      for (int ea : a){
                
        elementosEncontrados.add(ea);
      }
      for (int eb : b){
                
        if (!elementosEncontrados.contains(eb)) {
          
          elementosEncontrados.add(eb);
        }
      }
      
      elementosEncontrados.sort(null);
      
      out = new int[elementosEncontrados.size()];
            
      for (int i = 0; i < elementosEncontrados.size(); i++) {
                
        out[i] = elementosEncontrados.get(i);
      }
      return out;
    }
    
    /*
     * És `p` una partició d'`a`?
     *
     * `p` és un array de conjunts, haureu de comprovar que siguin elements d'`a`. Podeu suposar que
     * tant `a` com cada un dels elements de `p` està ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] p) {
      boolean elementoEncontrado;
      int aux = 0;
      
      for (int [] ep : p) {
          
          aux += ep.length;
      }
      if (aux != a.length) return false;
      
      for (int [] ep1 : p) {
          
        for (int [] ep2 : p) {
              
          if (ep1 != ep2) {
                  
            if (interseccion(ep1, ep2).length != 0) {
                        
              return false;
            }
          }
        }
      }
      for (int [] ep : p) {
            
        for (int eep : ep) {
              
          elementoEncontrado = false;
              
          for (int ea : a) {
                    
            if (eep == ea) {
                      
              elementoEncontrado = true;
            }
          }
          if (!elementoEncontrado) {
                   
            return false;
          }
        }
      }
      return true;
    }

    /*
     * Comprovau si la relació `rel` definida sobre `a` és un ordre parcial i que `x` n'és el mínim.
     *
     * Podeu soposar que `x` pertany a `a` i que `a` està ordenat de menor a major.
     */
    static boolean exercici2(int[] a, int[][] rel, int x) {
      int elementosReflexivos = 0;
      int elementoMinimo = a[0];
      boolean relacionEncontrada;
        
      for (int [] erel : rel) {
            
        if (erel[1] == elementoMinimo) {
                
          elementoMinimo = erel[0];
        }
            
        if (erel[0] == erel[1]) {
                
          elementosReflexivos++;
        }
            
        for (int [] erelAux : rel) {
                
          if (erel[0] != erel[1]) {
                    
            if ((erel[0] == erelAux[1])&&(erel[1] == erelAux[0])) return false;
          }
          relacionEncontrada = false;
                
          if (erel[1] == erelAux[0]) {
                    
            for (int [] aux : rel) {
                        
              if ((erel[0] == aux[0])&&(erelAux[1] == aux[1])) {
                            
                relacionEncontrada = true;
              }
            }
            if (!relacionEncontrada) return false;
          }
        }
      }
      return ((elementoMinimo == x)&&(elementosReflexivos == a.length));
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Trobau l'antiimatge de
     * `y` (ordenau el resultat de menor a major, podeu utilitzar `Arrays.sort()`). Podeu suposar
     * que `y` pertany a `codom` i que tant `dom` com `codom` també estàn ordenats de menor a major.
     */
    static int[] exercici3(int[] dom, int[] codom, Function<Integer, Integer> f, int y) {
      int [] tmp = new int[dom.length];
      int indice = 0;
       
      for (int edom : dom) {
           
        if (f.apply(edom) == y) {
                
          tmp[indice] = edom;
          indice++;
        }
      }
      int [] ai = new int[indice];
       
      for (int i = 0; i < indice; i++) {
           
        ai[i] = tmp[i];
      }
      return ai;
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
     * - 3 si `f` és bijectiva
     * - 2 si `f` només és exhaustiva
     * - 1 si `f` només és injectiva
     * - 0 en qualsevol altre cas
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major. Per comoditat, podeu
     * utilitzar les constants definides a continuació:
     */
    static final int NOTHING_SPECIAL = 0;
    static final int INJECTIVE = 1;
    static final int SURJECTIVE = 2;
    static final int BIJECTIVE = INJECTIVE + SURJECTIVE;

    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
      boolean injective = true;
      boolean surjective = true;
      
      for (int b : codom){
        int antiimagenes = 0;
        for (int a : dom){
            if (f.apply(a) == b) antiimagenes++;
        }
        if (antiimagenes > 1) injective = false;
        if (antiimagenes < 1) surjective = false;
      }
      
      if (injective && surjective) return BIJECTIVE;
      else if (injective) return INJECTIVE;
      else if (surjective) return SURJECTIVE;
      else return NOTHING_SPECIAL;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `p` és una partició d'`a`?

      assertThat(
          exercici1(
              new int[] { 1, 2, 3, 4, 5 },
              new int[][] { {1, 2}, {3, 5}, {4} }
          )
      );

      assertThat(
          !exercici1(
              new int[] { 1, 2, 3, 4, 5 },
              new int[][] { {1, 2}, {5}, {1, 4} }
          )
      );

      // Exercici 2
      // és `rel` definida sobre `a` d'ordre parcial i `x` n'és el mínim?

      ArrayList<int[]> divisibility = new ArrayList<int[]>();

      for (int i = 1; i < 8; i++) {
        for (int j = 1; j <= i; j++) {
          if (i % j == 0) {
            // i és múltiple de j, és a dir, j|i
            divisibility.add(new int[] { j, i });
          }
        }
      }

      assertThat(
          exercici2(
              new int[] { 1, 2, 3, 4, 5, 6, 7 },
              divisibility.toArray(new int[][] {}),
              1
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3 },
              new int[][] { {1, 1}, {2, 2}, {3, 3}, {1, 2}, {2, 3} },
              1
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3, 4, 5, 6, 7 },
              divisibility.toArray(new int[][] {}),
              2
          )
      );

      // Exercici 3
      // calcular l'antiimatge de `y`

      assertThat(
          Arrays.equals(
              new int[] { 0, 2 },
              exercici3(
                  new int[] { 0, 1, 2, 3 },
                  new int[] { 0, 1 },
                  x -> x % 2, // residu de dividir entre 2
                  0
              )
          )
      );

      assertThat(
          Arrays.equals(
              new int[] { },
              exercici3(
                  new int[] { 0, 1, 2, 3 },
                  new int[] { 0, 1, 2, 3, 4 },
                  x -> x + 1,
                  0
              )
          )
      );

      // Exercici 4
      // classificar la funció en res/injectiva/exhaustiva/bijectiva

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3 },
              x -> (x + 1) % 4
          )
          == BIJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3, 4 },
              x -> x + 1
          )
          == INJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1 },
              x -> x / 2
          )
          == SURJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3 },
              x -> x <= 1 ? x+1 : x-1
          )
          == NOTHING_SPECIAL
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Aritmètica).
   *
   */
  static class Tema3 {
    /*
     * Donat `a`, `b` retornau el màxim comú divisor entre `a` i `b`.
     *
     * Podeu suposar que `a` i `b` són positius.
     */
    static int exercici1(int a, int b) {
      int aux;
        
      if (a < b) {
            
        aux = a; a = b; b = aux;
      }
        
      while (b != 0) {
            
        aux = a; a = b; b = aux%b;
      }
      return a;
    }

    /*
     * Es cert que `a``x` + `b``y` = `c` té solució?.
     *
     * Podeu suposar que `a`, `b` i `c` són positius.
     */
    static boolean exercici2(int a, int b, int c) {
      return (c%exercici1(a, b) == 0);
    }

    /*
     * Quin es l'invers de `a` mòdul `n`?
     *
     * Retornau l'invers sempre entre 1 i `n-1`, en cas que no existeixi retornau -1
     */
    static int exercici3(int a, int n) {
      for (int i = 1; i < n; i++) {
            
        if ((a*i)%n == 1) {
                
          return i;
        }
      }
      return -1;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `mcd(a,b)`

      assertThat(
              exercici1(2, 4) == 2
      );

      assertThat(
              exercici1(1236, 984) == 12
      );

      // Exercici 2
      // `a``x` + `b``y` = `c` té solució?

      assertThat(
              exercici2(4,2,2)
      );
      assertThat(
              !exercici2(6,2,1)
      );
      // Exercici 3
      // invers de `a` mòdul `n`
      assertThat(exercici3(2, 5) == 3);
      assertThat(exercici3(2, 6) == -1);
    }
  }

  static class Tema4 {
    private static boolean esConexo(int[][] A) {
        
      int [] elementosConexos = new int[A.length];
      int [] elementosComprobados = new int[A.length];
        
      boolean hanHabidoCambios, todosElementosSonConexos;
        
      for (int i = 0; i < A.length; i++) {
            
        elementosConexos[i] = (i == 0) ? 1 : 0;
        elementosComprobados[i] = 0;
      }
        
      while (true) {
            
        hanHabidoCambios = false;

        for (int i = 0; i < A.length; i++) {

          if ((elementosConexos[i] == 1)&&(elementosComprobados[i] == 0)) {

            for (int j = 0; j < A.length; j++) {

              if (A[i][j] == 1) {

                elementosConexos[j] = 1;
                hanHabidoCambios = true;
              }
            }
            elementosComprobados[i] = 1;
          }
        }
        if (!hanHabidoCambios) {

          return false;
        }else {
          todosElementosSonConexos = true;

          for (int i = 0; i < A.length; i++) {

            if (elementosConexos[i] == 0) {

              todosElementosSonConexos = false;
            }
          }
          if (todosElementosSonConexos) {

            return true;
          }
        }
      }
    }
    
    /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, retornau l'ordre i la mida del graf.
     */
    static int[] exercici1(int[][] A) {
      int[] resultado = new int[2];
      resultado[0] = A.length;
      
      for (int i = 0; i < A.length; i++){
          for (int j = i+1; j < A.length; j++){
              if (A[i][j] == 1) resultado[1]++;
          }
      }
      
      return resultado;
    }

    /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, digau si el graf es eulerià.
     */
    static boolean exercici2(int[][] A) {
      int grado;
        
      if (!esConexo(A)) {
            
        return false;
      }
        
      for (int i = 0; i < A.length; i++) {
            
        grado = 0;
            
        for (int j = 0; j < A.length; j++) {
                
          if (A[i][j] == 1) {
                    
            grado++;
          }
        }
        if (grado%2 != 0) {
                
          return false;
        }
      }
      return true;
    }

    /*
     * Donat `n` el número de fulles d'un arbre arrelat i `d` el nombre de fills dels nodes interiors i de l'arrel,
     * retornau el nombre total de vèrtexos de l'arbre
     *
     */
    static int exercici3(int n, int d) {
      int nodosTotales = 0;
      int nodosPorContar = n;
        
      while (true) {
            
        nodosTotales += nodosPorContar - nodosPorContar%d;
        nodosPorContar = nodosPorContar/d + nodosPorContar%d;
            
        if (nodosPorContar == 1) return nodosTotales + 1;
      }
    }

    /*
     * Donada una matriu d'adjacencia `A` d'un graf connex no dirigit, digau si el graf conté algún cicle.
     */
    static boolean exercici4(int[][] A) {
      int[][] auxiliar;
      int nodoFinal;
      int aristas, recorrido;
      
      aristas = exercici1(A)[1];
      
      for (int i = 0; i < A.length; i++) {
        auxiliar = A.clone();
        nodoFinal = i;
        recorrido = 0;
        
        for (int j = 0; j < A.length; j++){
            
          if (auxiliar[nodoFinal][j] == 1){
            auxiliar[nodoFinal][j] = 0;
            auxiliar[j][nodoFinal] = 0;
            recorrido++;
            nodoFinal = j;
            
            if (i == nodoFinal) return true;
            if (recorrido > aristas) break;
            
            j = -1;
          }
        }
      }
      return false;
    }
    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `ordre i mida`

      assertThat(
              Arrays.equals(exercici1(new int[][] { {0, 1, 0}, {1, 0, 1}, {0,1, 0}}), new int[] {3, 2})
      );

      assertThat(
              Arrays.equals(exercici1(new int[][] { {0, 1, 0, 1}, {1, 0, 1, 1}, {0 , 1, 0, 1}, {1, 1, 1, 0}}), new int[] {4, 5})
      );

      // Exercici 2
      // `Es eulerià?`

      assertThat(
              exercici2(new int[][] { {0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
      );
      assertThat(
              !exercici2(new int[][] { {0, 1, 0}, {1, 0, 1}, {0,1, 0}})
      );
      // Exercici 3
      // `Quants de nodes té l'arbre?`
      assertThat(exercici3(5, 2) == 9);
      assertThat(exercici3(7, 3) == 10);

      // Exercici 4
      // `Conté algún cicle?`
      assertThat(
              exercici4(new int[][] { {0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
      );
      assertThat(
              !exercici4(new int[][] { {0, 1, 0}, {1, 0, 1}, {0, 1, 0}})
      );

    }
  }


  /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
