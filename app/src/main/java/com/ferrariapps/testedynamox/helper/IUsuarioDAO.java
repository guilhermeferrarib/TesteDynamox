package com.ferrariapps.testedynamox.helper;
import com.ferrariapps.testedynamox.model.Usuario;

import java.util.List;

public interface IUsuarioDAO {

    public boolean salvar(Usuario usuario);

    public boolean atualizar(Usuario usuario);

    public List<Usuario> listar();
}
