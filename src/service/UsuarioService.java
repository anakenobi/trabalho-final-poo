package service;

import model.*;
import exception.*;
import model.usuario.Administrador;
import model.usuario.Aluno;
import model.usuario.Professor;
import model.usuario.Usuario;

import java.util.*;

public class UsuarioService {

    private List<Usuario> usuarios;

    public UsuarioService()
    {
        this.usuarios = new ArrayList<>();
    }

    public Aluno cadastrarAluno(String nome, String email, String senha, String matricula)
    {
        Aluno aluno = new Aluno(nome, email, senha, matricula);
        usuarios.add(aluno);
        return aluno;
    }

    public Professor cadastrarProfessor(String nome, String email, String senha, String departamento)
    {
        Professor professor = new Professor(nome, email, senha, departamento);
        usuarios.add(professor);
        return professor;
    }

    public Administrador cadastrarAdministrador(String nome, String email, String senha, String cargo)
    {
        Administrador administrador = new Administrador(nome, email, senha, cargo);
        usuarios.add(administrador);
        return administrador;
    }

    public Usuario buscarPorId(int id) throws UsuarioNaoEncontradoException
    {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: ID " + id));
    }

    public Usuario buscarPorEmail(String email) throws UsuarioNaoEncontradoException
    {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + email));
    }

    public void removerUsuario(int id) throws UsuarioNaoEncontradoException
    {
        Usuario usuario = buscarPorId(id);
        usuarios.remove(usuario);
    }

    public List<Usuario> listarTodos()
    {
        return new ArrayList<>(usuarios);
    }

    public List<Aluno> listarAlunos()
    {
        return usuarios.stream()
                .filter(u -> u instanceof Aluno)
                .map(u -> (Aluno) u)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }


}
