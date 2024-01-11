import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class App {

    private JFrame frame;
    private JPanel mainPanel;
    private String localizacao;

    public ArrayList<Dia> previsoes(String local) throws IOException{
        Document doc = Jsoup.connect("https://www.google.com/search?q=clima+"+local+"&oq=cli&gs_lcrp=EgZjaHJvbWUqBggAEEUYOzIGCAAQRRg7MgYIARBFGDsyBggCEEUYOzINCAMQABiDARixAxiABDINCAQQABiDARixAxiABDIGCAUQRRg8MgYIBhBFGDwyBggHEEUYPdIBCDExMDdqMGo3qAIAsAIA&sourceid=chrome&ie=UTF-8").get();
        
        localizacao = doc.select("div.eKPi4").select(".BBwThe").text();

        String dia = doc.select("div.Z1VzSb").text();
        String[] dias = dia.split(" ");//array com dias
        
        String max = doc.select("div.gNCp2e").select("[style*=display:inline]").text();
        String[] maxims = max.split(" ");//array com maximas
      
        String min = doc.getElementsByClass("QrNVmd ZXCv8e").select("[style*=display:inline]").text();
        String[] mins = min.split(" ");//array com minimas

        LinkedList<String> a = new LinkedList<>();
        doc.getElementsByClass("DxhUm").select("img").stream().forEach(s -> a.add(s.attr("alt")));
        String[] clima = a.toArray(new String[0]);//array com climas

        ArrayList<Dia> previsoes = new ArrayList<>();//cria lista de dias
        for (int i = 0; i < dias.length; i++) {
            previsoes.add(new Dia(dias[i], maxims[i]+"°C", mins[i]+"°C", clima[i]));
        }
        return previsoes;
    }

    public JFrame frame() throws IOException{
        frame = new JFrame();  
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(input());
        mainPanel.add(table("porto+alegre+rs"));

        frame.setContentPane(mainPanel);

        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        return frame;
    }

    public void atualiza(String local) throws IOException{
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(input());
        mainPanel.add(table(local));
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }
    
    public JPanel table(String local) throws IOException{
        TableModel tm = new TableModel(previsoes(local));
        JTable table = new JTable(tm);
        JPanel tableP = new JPanel();
        tableP.setLayout(new BoxLayout(tableP, BoxLayout.PAGE_AXIS));
        tableP.add(new JLabel(localizacao));
        tableP.add(table);
        return tableP;
    }
    public JPanel input(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JLabel label = new JLabel("Localização");
        panel.add(label);

        JTextField tf = new JTextField();
        panel.add(tf);
        
        JButton busca = new JButton("Buscar");
        busca.addActionListener(b -> {
            try {
                atualiza(tf.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        panel.add(busca);
        panel.setVisible(true);
        return panel;
    }
    public static void main(String[] args) throws Exception {
        App a = new App();
        a.frame();
    }
}
