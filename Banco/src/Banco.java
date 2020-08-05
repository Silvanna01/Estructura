import java.io.*;
import java.util.Calendar;

class Nodo{
    String nombre;
    String apellido;
    String cedula;
    String prioridad;
    Nodo siguiente;

    Nodo(){
        nombre = "";
        apellido = "";
        cedula = "";
        prioridad = "";
        siguiente = null;
    }

    Nodo(String nomb, String ape, String ced, String pri){
        nombre = nomb;
        apellido = ape;
        cedula = ced;
        prioridad = pri;
        siguiente = null;
    }
}

class Pila{
    Nodo top;
    Pila(){
        top = null;
    }

    boolean Vacia(){
        return (top == null);
    }

    void Apilar(String nomb, String ape, String ced, String pri){
        Nodo aux = new Nodo(nomb,ape,ced,pri);
        aux.siguiente = top;
        top = aux;
    }
    
    Nodo Desapilar(){
        Nodo aux = top;
        if(Vacia()){
            System.out.println("Pila Vacia");
        }else{
            top = top.siguiente;
        }
        return aux;
    }
}

class Cola{
    Nodo front;
    Cola(){
        front = null;
    }

    boolean Vacia(){
        return front == null;
    }

    int Tamaño(){
        Nodo aux = front;
        int cont = 0; 
        while (aux != null){
            cont++;
            aux = aux.siguiente;
        }
        return cont;
    }

    void Encolar(String nomb, String ape, String ced, String pri){
        Nodo aux = front;
        if (Vacia()){
            front = new Nodo(nomb,ape,ced,pri);
        }else{
            while (aux.siguiente != null)
                aux = aux.siguiente;
            aux.siguiente = new Nodo(nomb,ape,ced,pri);
            aux = aux.siguiente;
        }
    }

    Nodo Desencolar(){
        Nodo aux = front;
        if (Vacia()){
            System.out.println("Cola Vacía");
        }else{
            front = front.siguiente;
        }
        return aux;
    }
}

class Lista{
    Nodo principio, fin;
    Lista(){
        principio = null;
        fin = null;
    }

    boolean Vacia(){
        return principio == null;
    }
    
    void llenarLista(String nomb, String ape, String ced, String pri){
        if (!Vacia()){
            fin.siguiente = new Nodo(nomb,ape,ced,pri);
            fin = fin.siguiente;
        }else{
            principio = fin = new Nodo(nomb,ape,ced,pri);
        }
    }
}

class Clientes{

    double tiempo = 450;
    int h = 8;
    int m = 00;
    String tipo = "AM";
    int cont_atendidos = 0;
    Calendar C = Calendar.getInstance();
    String dia = Integer.toString(C.get(Calendar.DATE));
    String mes = Integer.toString(C.get(Calendar.MONTH));
    String año = Integer.toString(C.get(Calendar.YEAR));
    int d = Integer.parseInt(dia) - 1;
    int mess = Integer.parseInt(mes)+1;
    Lista ObjLista = new Lista();
    Pila ObjPila = new Pila();
    Cola ObjCola = new Cola();
    
    void CargarClientes(String pnomb) throws IOException{
        FileReader fr;
        fr = new FileReader(pnomb);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        while((linea=br.readLine())!=null){
            String datos[] = new String[3];
            datos = linea.split("/");
            ObjCola.Encolar(datos[0],datos[1],datos[2],datos[3]);
        }
        fr.close();
    }

    void Guardar(String pnomb, String pdatos) throws IOException{
        PrintWriter pr;
        pr = new PrintWriter(new FileWriter(pnomb,true));
        pr.println(pdatos);
        pr.close();
    }

    int Prioridad(){
        Nodo aux = ObjCola.front;
        int cont = 0;
        while(aux != null){
            if(aux.prioridad.equals("discapacidad")||aux.prioridad.equals("terceraedad")||aux.prioridad.equals("embarazada")){
                cont++;
            }
            aux = aux.siguiente;
        }
        return cont;
    }

    void AtenderCliente() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int opc = 0;
        int cont_tramites = 0;
        String tipo_tramite = "";
        String[] tramite = new String[5];
        do{
            int i = 0;
            do{
                System.out.println("");
                System.out.println("Clientes: "+ObjCola.Tamaño());
                System.out.println("Fecha: "+String.format("%02d", Integer.parseInt(dia))+"/"+String.format("%02d", mess)+"/"+año+" Hora: "+h+":"+String.format("%02d", m)+tipo);
                System.out.println("Bienvenido(a): "+ObjCola.front.nombre+" "+ObjCola.front.apellido);
                System.out.println("");
                System.out.println("..:: Solicitudes ::..");
                System.out.println("1: Retiro");
                System.out.println("2: Deposito");
                System.out.println("3: Consulta");
                System.out.println("4: Act. Libreta");
                System.out.println("5: Pago Servicios");
                System.out.println("6: Finalizar Solicitud");
                opc = Integer.parseInt(br.readLine());
                switch (opc) {
                    case 1:
                        m = m + 4;
                        tiempo = tiempo - 4;
                        tipo_tramite = "Retiro";
                        cont_tramites++;
                        break;
                    case 2:
                        m = m + 3;
                        tiempo = tiempo - 3;
                        tipo_tramite = "Deposito";
                        cont_tramites++;
                        break;
                    case 3:
                        m = m + (int)1.5;
                        tiempo = tiempo - 1.5;
                        tipo_tramite = "Consulta";
                        cont_tramites++;
                        break;
                    case 4:
                        m = m + 5;
                        tiempo = tiempo - 5;
                        tipo_tramite = "Act. Libreta";
                        cont_tramites++;
                        break;
                    case 5:
                        m = m + 2;
                        tiempo = tiempo - 2;
                        tipo_tramite = "Pago Servicios";
                        cont_tramites++;
                        break;
                    default:
                        break;
                }
                if (m==60){
                    m=0;
                    h=h+1;
                }else if (m>60){
                    int n = m - 60;
                    m = n;
                    h=h+1;
                }
                if (h>=12){
                    tipo = "PM";
                }
                tramite[i] = tipo_tramite;
                i++;
            }while(opc!=6 && cont_tramites<=4 && tiempo>0);
            for (int j = 0; j < tramite.length; j++) {
                if(tramite[j]=="" || tramite[j]==null){
                    tramite[j] = "-";
                }
            }
            if (cont_atendidos<=3){
                cont_atendidos++;
            }else{
                cont_atendidos = 0;
            }
            Nodo aux = ObjCola.Desencolar();
            Cola ColaAux = new Cola();
            Cola Caux = new Cola();
            ObjLista.llenarLista(aux.nombre,aux.apellido,aux.cedula,aux.prioridad+" "+tramite[0]+" "+tramite[1]+" "+tramite[2]+" "+tramite[3]+" "+tramite[4]);
            if (ObjCola.Tamaño()>0 && tiempo>0){
                if(cont_atendidos<4){
                    AtenderCliente();
                }else if(cont_atendidos==4 && Prioridad()>0){
                    Nodo aux_pri = new Nodo();
                    while(ObjCola.front!=null){
                        if(ObjCola.front.prioridad.equals("normal")){
                            Nodo aux_col = ObjCola.Desencolar();
                            ColaAux.Encolar(aux_col.nombre, aux_col.apellido, aux_col.cedula, aux_col.prioridad);
                        }else{
                            aux_pri = ObjCola.Desencolar();
                            break;
                        }
                    }
                    Caux.Encolar(aux_pri.nombre, aux_pri.apellido, aux_pri.cedula, aux_pri.prioridad);
                    while(ColaAux.front!=null){
                        Nodo auxi = ColaAux.Desencolar();
                        Caux.Encolar(auxi.nombre, auxi.apellido, auxi.cedula, auxi.prioridad);
                    }
                    while(ObjCola.front!=null){
                        Nodo auxi2 = ObjCola.Desencolar();
                        Caux.Encolar(auxi2.nombre, auxi2.apellido, auxi2.cedula, auxi2.prioridad);
                    }
                    while(Caux.front!=null){
                        Nodo auxi3 = Caux.Desencolar();
                        ObjCola.Encolar(auxi3.nombre, auxi3.apellido, auxi3.cedula, auxi3.prioridad);
                    }
                    AtenderCliente();
                }else{
                    AtenderCliente();
                }
            }else{
                System.out.println("Taquilla Cerrada!");
                File Taq = new File("Taquilla.log");
                File nTaq = new File("Taquilla"+String.format("%02d", d)+"-"+String.format("%02d", mess)+"-"+año+".log");
                if (Taq.exists()){
                    Taq.renameTo(nTaq);
                }
                Nodo aux2 = ObjLista.principio;
                while (aux2 != null){
                    ObjPila.Apilar(aux2.nombre,aux2.apellido,aux2.cedula,aux2.prioridad);
                    aux2 = aux2.siguiente;
                }
                Nodo aux3 = ObjPila.top;
                while (aux3 != null){
                    String datos = aux3.nombre+" "+aux3.apellido+" "+aux3.cedula+" "+aux3.prioridad;
                    Guardar("Taquilla.log", datos);
                    aux3 = aux3.siguiente;
                }
                if(ObjCola.Tamaño()>0){
                    Nodo aux_pend = ObjCola.front;
                    while (aux_pend != null){
                        String datos_pend = aux_pend.nombre+"/"+aux_pend.apellido+"/"+aux_pend.cedula+"/"+aux_pend.prioridad;
                        Guardar("clientes_pendientes.in",datos_pend);
                        aux_pend = aux_pend.siguiente;
                    }
                }
            }
        }while(ObjCola.Tamaño()>0 && tiempo>0);
    }
}

public class Banco {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File Pendientes = new File("clientes_pendientes.in");
        int opc = 0;
        do{
            System.out.println("");
            System.out.println("..::: BANESCO :::..");
            System.out.println("1: Abrir Taquilla");
            System.out.println("2: Salir");
            opc = Integer.parseInt(br.readLine());
            if (opc == 1){
                Clientes Cliente = new Clientes();
                if (Pendientes.exists()){
                    Cliente.CargarClientes("clientes_pendientes.in");
                    Pendientes.delete();                    
                }
                Cliente.CargarClientes("Clientes.in");
                Cliente.AtenderCliente();
            }
        }while(opc!=2);
    }
    
}
