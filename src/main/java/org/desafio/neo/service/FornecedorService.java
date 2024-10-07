package org.desafio.neo.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.desafio.neo.model.Fornecedor;
import org.desafio.neo.repository.FornecedorRepository;
import org.desafio.neo.utils.CNPJUtils;
import org.desafio.neo.utils.EmailUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class FornecedorService {
    private FornecedorRepository fornecedorRepository;

    public FornecedorService(Session session){
        this.fornecedorRepository = new FornecedorRepository(session);
    }

    public JsonObject createFornecedores(JsonArray jsonArray) {
        List<Fornecedor> fornecedoresCriados = new ArrayList<>();
        List<JsonObject> fornecedoresComErro = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            try {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

                Fornecedor fornecedor = new Gson().fromJson(jsonObject, Fornecedor.class);
                if (!CNPJUtils.isValidCNPJ(fornecedor.getCnpj())) {
                    throw new RuntimeException("CNPJ Inválido");
                }
                if (!EmailUtils.isValidEmail(fornecedor.getEmail())) {
                    throw new RuntimeException("E-mail Inválido");
                }

                fornecedorRepository.save(fornecedor);
                fornecedoresCriados.add(fornecedor);
            } catch (Exception e) {
                fornecedoresComErro.add(jsonArray.get(i).getAsJsonObject());
                e.printStackTrace();
            }
        }

        JsonObject responseJson = new JsonObject();
        responseJson.add("cadastrosCriados", new Gson().toJsonTree(fornecedoresCriados));
        responseJson.add("cadastrosComErro", new Gson().toJsonTree(fornecedoresComErro));

        return responseJson;
    }

    public Fornecedor getFornecedorById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public List<Fornecedor> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    public List<Fornecedor> getAllPaginated(int page, int size) {
        return fornecedorRepository.findAllPaginated(page,size);
    }

    public Fornecedor updateFornecedor(Fornecedor fornecedorAtualizado) {
        Fornecedor fornecedorExistente = fornecedorRepository.findById(fornecedorAtualizado.getId());

        if (fornecedorExistente == null) {
            throw new RuntimeException("Fornecedor não encontrado com o ID: " + fornecedorAtualizado.getId());
        }

        fornecedorExistente.setName(fornecedorAtualizado.getName());
        fornecedorExistente.setEmail(fornecedorAtualizado.getEmail());
        fornecedorExistente.setDescription(fornecedorAtualizado.getDescription());
        fornecedorExistente.setCnpj(fornecedorAtualizado.getCnpj());

        fornecedorRepository.update(fornecedorExistente);

        return fornecedorExistente;
    }

    public void deleteFornecedor(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null) {
            throw new RuntimeException("Fornecedor não encontrado com o ID: " + id);
        }

        fornecedorRepository.delete(fornecedor);
    }
}
