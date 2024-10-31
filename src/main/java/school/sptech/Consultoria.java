package school.sptech;

import school.sptech.especialistas.DesenvolvedorMobile;
import school.sptech.especialistas.DesenvolvedorWeb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Consultoria {
    private String nome;
    private Integer vagas;
    private final List<Desenvolvedor> desenvolvedores;

    public Consultoria(String nome, Integer vagas) {
        this.nome = nome;
        this.vagas = vagas;
        this.desenvolvedores = new ArrayList<>();
    }

    public Consultoria() {
        this.desenvolvedores = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public List<Desenvolvedor> getDesenvolvedores() {
        return desenvolvedores;
    }

    public void contratar(Desenvolvedor desenvolvedor) {
        if (vagas > this.desenvolvedores.size() && desenvolvedor != null) {
            desenvolvedores.add(desenvolvedor);
        }
    }

    public void contratarFullstack(DesenvolvedorWeb desenvolvedor) {
        if (desenvolvedor.isFullstack()) {
            contratar(desenvolvedor);
        }
    }

    public Double getTotalSalarios() {
        return desenvolvedores.stream().map(Desenvolvedor::calcularSalario)
                .reduce(Double::sum).orElse(0.);
    }

    public Integer qtdDesenvolvedoresMobile() {
        return (int) desenvolvedores.stream()
                .filter(desenvolvedor -> desenvolvedor instanceof DesenvolvedorMobile)
                .count();
    }

    public List<Desenvolvedor> buscarPorSalarioMaiorIgualQue(Double salario) {
        return desenvolvedores.stream()
                .filter(desenvolvedor -> desenvolvedor.calcularSalario() >= salario)
                .toList();
    }

    public Desenvolvedor buscarMenorSalario() {
        return desenvolvedores.stream()
                .min(Comparator.comparing(Desenvolvedor::calcularSalario))
                .orElse(null);
    }

    public List<Desenvolvedor> buscarPorTecnologia(String tecnologia) {
        return desenvolvedores.stream()
                .filter(desenvolvedor -> (desenvolvedor instanceof DesenvolvedorMobile mobile &&
                        (mobile.getLinguagem().equalsIgnoreCase(tecnologia) || mobile.getPlataforma().equalsIgnoreCase(tecnologia)))
                        || (desenvolvedor instanceof DesenvolvedorWeb web &&
                        (web.getSgbd().equals(tecnologia) || web.getBackend().equals(tecnologia) || web.getFrontend().equals(tecnologia))))
                .toList();
    }

    public Double getTotalSalariosPorTecnologia(String tecnologia) {
        return buscarPorTecnologia(tecnologia).stream()
                .map(Desenvolvedor::calcularSalario)
                .reduce(Double::sum).orElse(.0);
    }
}
