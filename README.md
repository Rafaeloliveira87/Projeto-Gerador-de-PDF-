# Relatório Semear

## 📝 Sobre o Projeto

O **Relatório Semear** é uma ferramenta desenvolvida em Java para automatizar a criação de relatórios mensais para a ONG. A aplicação possui uma interface gráfica simples, onde o usuário insere o nome de uma atividade e o sistema gera, de forma automática, um texto padronizado. O principal diferencial é a capacidade de salvar esse relatório em formato PDF, com um nome de arquivo organizado por atividade e data.

O projeto foi criado para economizar tempo, padronizar a documentação e facilitar o arquivamento de relatórios importantes.

## ✨ Funcionalidades

- **Geração Automática de Texto:** Insira o nome da atividade para preencher um modelo de texto pré-definido.
- **Interface Intuitiva:** Uma interface gráfica limpa e fácil de usar, ideal para quem não tem conhecimento técnico.
- **Exportação para PDF:** Salve o relatório como um arquivo PDF, um formato universal e profissional.
- **Nomenclatura Inteligente:** O nome do arquivo PDF é gerado automaticamente com base na atividade e na data, evitando a confusão de arquivos.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java
- **Interface Gráfica (GUI):** Java Swing
- **Geração de PDF:** Apache PDFBox

## 🚀 Como Executar o Projeto

Para rodar o projeto, siga estes passos:

1.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git](https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git)
    cd SEU-REPOSITORIO
    ```
    *(Lembre-se de substituir `SEU-USUARIO` e `SEU-REPOSITORIO` pelos seus dados do GitHub.)*

2.  **Adicione a Biblioteca Apache PDFBox:**
    - Baixe a biblioteca `pdfbox-2.x.x.jar` no site oficial do Apache PDFBox.
    - Adicione o arquivo `.jar` ao `classpath` do seu projeto na sua IDE (IntelliJ, Eclipse, etc.).

3.  **Execute a Classe Principal:**
    - Execute a classe `RelatorioSemear.java` diretamente.

## 📂 Estrutura de Arquivos
    .
    ├──Class RelatorioSemear
                          └──src/
                               └── RelatorioSemear.java

## 🤝 Contribuições

Sinta-se à vontade para abrir issues ou sugerir melhorias.                               
