import java.awt.Color; // Importe a classe Color
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;


public class RelatorioSemear {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Relatório da ONG");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Definindo a cor de fundo do frame para um laranja suave
        Color lightOrange = new Color(255, 240, 220); // Exemplo de laranja suave
        frame.getContentPane().setBackground(lightOrange); // Define o fundo do painel de conteúdo

        JLabel label = new JLabel("Atividade:");
        label.setBounds(20, 20, 100, 25);
        label.setForeground(Color.BLACK); // Cor do texto da label
        frame.add(label);

        JTextField campoAtividade = new JTextField();
        campoAtividade.setBounds(120, 20, 300, 25);
        campoAtividade.setBackground(Color.WHITE); // Fundo branco para o campo de texto
        campoAtividade.setForeground(Color.BLACK); // Cor do texto digitado
        frame.add(campoAtividade);

        JTextArea campoTexto = new JTextArea();
        campoTexto.setBounds(20, 60, 440, 200);
        campoTexto.setLineWrap(true);
        campoTexto.setWrapStyleWord(true);
        campoTexto.setBackground(Color.WHITE); // Fundo branco para a área de texto
        campoTexto.setForeground(Color.BLACK); // Cor do texto
        frame.add(campoTexto);

        JButton botaoGerar = new JButton("Gerar texto");
        botaoGerar.setBounds(20, 270, 150, 30);
        botaoGerar.setBackground(new Color(255, 200, 150)); // Laranja um pouco mais forte para o botão
        botaoGerar.setForeground(Color.BLACK); // Cor do texto do botão
        botaoGerar.setFocusPainted(false); // Remove a borda de foco
        frame.add(botaoGerar);

        JButton botaoPDF = new JButton("Salvar como PDF");
        botaoPDF.setBounds(180, 270, 150, 30);
        botaoPDF.setBackground(new Color(255, 200, 150)); // Laranja um pouco mais forte para o botão
        botaoPDF.setForeground(Color.BLACK); // Cor do texto do botão
        botaoPDF.setFocusPainted(false); // Remove a borda de foco
        frame.add(botaoPDF);

        botaoGerar.addActionListener(e -> {
            String atividade = campoAtividade.getText();
            String texto = "Acolhimento/ Café da manhã/ Lanche/ Oficina Fala Sério com roda de conversa sobre "
                           + atividade + "/ Almoço.";
            campoTexto.setText(texto);
        });

        botaoPDF.addActionListener(e -> {
            String textoFinal = campoTexto.getText();

            String atividadeNome = campoAtividade.getText().trim();
            if (atividadeNome.isEmpty()) {
                atividadeNome = "Atividade";
            }
            atividadeNome = atividadeNome.replaceAll("[^a-zA-Z0-9.-]", "_");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = sdf.format(new Date());
            String nomeArquivoPadrao = "Relatorio_" + atividadeNome + "_" + timestamp + ".pdf";

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar Relatório PDF");
            fileChooser.setSelectedFile(new File(nomeArquivoPadrao));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos PDF (*.pdf)", "pdf"));

            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File arquivoParaSalvar = fileChooser.getSelectedFile();
                String caminhoArquivo = arquivoParaSalvar.getAbsolutePath();

                if (!caminhoArquivo.toLowerCase().endsWith(".pdf")) {
                    caminhoArquivo += ".pdf";
                }

                try {
                    PDDocument doc = new PDDocument();
                    PDPage page = new PDPage(PDRectangle.A4);
                    doc.addPage(page);

                    PDPageContentStream content = new PDPageContentStream(doc, page);
                    content.beginText();
                    PDType1Font font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
                    float fontSize = 12;
                    content.setFont(font, fontSize);
                    content.setLeading(14.5f);

                    float margin = 50;
                    float yStart = page.getMediaBox().getHeight() - margin;
                    float xStart = margin;
                    float width = page.getMediaBox().getWidth() - 2 * margin;

                    content.newLineAtOffset(xStart, yStart);

                    List<String> lines = splitTextIntoLines(textoFinal, font, fontSize, width);

                    for (String line : lines) {
                        content.showText(line);
                        content.newLine();
                    }

                    content.endText();
                    content.close();

                    doc.save(caminhoArquivo);
                    doc.close();
                    JOptionPane.showMessageDialog(frame, "PDF salvo com sucesso em: " + caminhoArquivo);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Erro ao gerar PDF: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Operação de salvar PDF cancelada.");
            }
        });

        frame.setVisible(true);
    }

    private static List<String> splitTextIntoLines(String text, PDType1Font font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new java.util.ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            float size = font.getStringWidth(currentLine.toString() + (currentLine.length() > 0 ? " " : "") + word) / 1000 * fontSize;

            if (size < maxWidth) {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                if (font.getStringWidth(word) / 1000 * fontSize > maxWidth) {
                    if (currentLine.length() > 0) {
                        lines.add(currentLine.toString());
                    }
                    lines.add(word);
                    currentLine = new StringBuilder();
                } else {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                }
            }
        }
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}