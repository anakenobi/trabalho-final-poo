package model.usuario;

public abstract class Usuario {


        private static int contadorId = 1;
        private int id;
        private String nome;
        private String email;
        private String senha;
        private boolean ativo;

    public Usuario(String nome, String email, String senha)
    {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.id = contadorId++;
        this.ativo = true;


    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }

    // padrao java para getters do tipo booleano
    public boolean isAtivo()
    {
        return ativo;
    }

    public void setAtivo(boolean ativo)
    {
        this.ativo = ativo;
    }

    public int getId()
    {
        return id;
    }

        // aqui obriga toda classe que herdar de usuario implemente esse metodo
        // ex: uma classe cliente retornaria cliente
    public abstract String getTipo();

    @Override
    public String toString()
    {
        return String.format("[%s] %s (%s) - %s", getTipo(), nome, email, ativo ? "Ativo" : "Inativo");
    }



}
