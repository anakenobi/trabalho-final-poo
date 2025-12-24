package model.usuario;

public class Administrador extends Usuario{

    private String cargo;

    public Administrador(String nome, String email, String senha, String cargo)
    {
        super(nome, email, senha);
        this.cargo = cargo;
    }

    public String getCargo()
    {
        return cargo;
    }

    public void setCargo(String cargo)
    {
        this.cargo = cargo;
    }

    @Override
    public String getTipo()
    {
        return "Administrador";
    }











}
