import java.io.*;
import java.util.Random;

class Nodo{
    String datos;
    Nodo siguiente;
    Nodo anterior;
}

class Lista{
    Nodo principio,fin;
    
    Lista(){
        principio = null;
        fin = null;
    }

    boolean ListaVacia(){
        return(principio==null);
    }

    void Participantes() throws IOException{
        FileReader fr;
        fr = new FileReader("jugadores.in");
        BufferedReader br = new BufferedReader(fr);
        String linea;
        while((linea = br.readLine())!=null){
            String[] datos = new String[2];
            String Jugador = "";
            datos = linea.split("/");
            for (int i = 0; i < datos.length; i++) {
                Jugador = Jugador + datos[i] + " ";
            }
            CargarJugador(Jugador);
        }

    }

    void Guardar(String pnomb, String pdatos) throws IOException{
        PrintWriter pr;
        pr = new PrintWriter(new FileWriter(pnomb, true));
        pr.println(pdatos);
        pr.close();
    }

    void CargarJugador(String datos){
        Nodo aux = new Nodo();
        aux.datos = datos;
        if (ListaVacia()){
            principio = aux;
            principio.siguiente = principio;
            principio.anterior = fin;
            fin = aux;
        }else{
            fin.siguiente = aux;
            aux.siguiente = principio;
            aux.anterior = fin;
            fin = aux;
            principio.anterior = fin;
        }
    }

    int CantJug() {
        Nodo aux = principio;
        int cont = 1;
        while(aux.siguiente!=principio){
            aux = aux.siguiente;
            cont++;
        }
        return cont;
    }

    void Horario() throws IOException{
        Nodo aux = principio;
        Random valor = new Random();
        int aleatorio = valor.nextInt(30);
        for (int i = 0; i < aleatorio; i++) {
            if (aux.siguiente!=null){
                aux = aux.siguiente;
            }
        }
        if (CantJug()>1){
            Guardar("perdedores.out", aux.datos);
            System.out.println("Le explotó la papa a: "+aux.datos);
            System.out.println("");
            EliminarJugador(aux.datos);
        }else{
            Guardar("ganador.out", aux.datos);
            System.out.println("");
            System.out.println("Ganador: "+aux.datos);
            System.out.println("");
            EliminarJugador(aux.datos);
        }
    }

    void AntiHorario() throws IOException{
        Nodo aux = fin;
        Random valor = new Random();
        int aleatorio = valor.nextInt(30);
        for (int i = 0; i < aleatorio; i++) {
            if (aux.anterior!=null){
                aux = aux.anterior;
            }
        }
        if (CantJug()>1){
            Guardar("perdedores.out", aux.datos);
            System.out.println("Le explotó la papa a: "+aux.datos);
            System.out.println("");
            EliminarJugador(aux.datos);
        }else{
            Guardar("ganador.out", aux.datos);
            System.out.println("");
            System.out.println("Ganador: "+aux.datos);
            System.out.println("");
            EliminarJugador(aux.datos);
        }
        
    }

    void EliminarJugador(String datos){
        Nodo aux = principio;
        Nodo aux2 = fin;
        if (principio==fin){
            principio = null;
            fin = null;
        }else{
            do{
                if (aux.datos.equals(datos)){
                    if (aux==principio){
                        principio = principio.siguiente;
                        principio.anterior = fin;
                        fin.siguiente = principio;
                    }else if (aux == fin){
                        fin = aux2;
                        fin.siguiente = principio;
                        principio.anterior = fin;
                    }else{
                        aux2.siguiente = aux.siguiente;
                        aux.siguiente.anterior = aux2;
                    }
                }
                aux2 = aux;
                aux = aux.siguiente;
            }while(aux!=principio);
        }
    }
}
public class PapaCaliente {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
        Lista ObjLista = new Lista();
        ObjLista.Participantes();
        int opc = 0;
        int opc2 = 0;
        do{
            System.out.println("La Papa Caliente");
            System.out.println("1: Jugar");
            System.out.println("2: Salir");
            opc2 = Integer.parseInt(br.readLine());
            if (opc2 == 1){
                System.out.println("Elegir Sentido:");
                System.out.println("1: Horario");
                System.out.println("2: AntiHorario");
                opc = Integer.parseInt(br.readLine());
                do{
                    if (opc == 1){
                        ObjLista.Horario();
                    }else if (opc == 2){
                        ObjLista.AntiHorario();
                    }   
                }while(ObjLista.ListaVacia()!=true);
            }
            ObjLista.Participantes();
        }while(opc2!=2);
    }
    
}
