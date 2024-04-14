import java.io.*
;
import java.util.LinkedList;
public class CreateArchive {
    BufferedWriter fw;
    CreateArchive(String Algorithm){
        try{
            fw = new BufferedWriter(new FileWriter("./Resultado "+Algorithm+".txt"),100000);
        }catch(IOException e){
            System.out.println("Erro ao criar Arquivo.");
        }
    }   
    public void writeComponents(LinkedList<LinkedList<Integer>> componentes){
        try{
            fw.write("Componentes:\n");
            for(LinkedList<Integer> element: componentes){
                fw.write("[");
                for(Integer elementLinkedList: element)
                    fw.write(elementLinkedList.toString()+",");
                fw.write("]\n");
            }
        }catch(Exception e){
           System.err.println(e);
        } 
    }
}
