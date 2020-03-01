package br.com.alura.agenda.dto;

import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

public class AlunoSync {
    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;


    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setMomentoDaUltimaModificacao(String momentoDaUltimaModificacao) {
        this.momentoDaUltimaModificacao = momentoDaUltimaModificacao;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
}
