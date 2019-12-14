/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.meumenu.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.meumenu.excel.PedidoExcel;
import br.com.meumenu.model.cardapio.Refeicao;

@ApplicationScoped
public class PedidoRepository {

	@Inject
	private EntityManager em;

	public List<PedidoExcel> findAllByDate(LocalDate now) {
		List<PedidoExcel> pedidos = new ArrayList<PedidoExcel>();
		PedidoExcel excel = new PedidoExcel();
		excel.setCliente("Cliente");
		excel.setObservacao("Observação");
		excel.setRefeicao("Refeição");
		excel.setTamanho("Tamanho");
		excel.setTelefone("3133968");
		pedidos.add(excel);
		return pedidos;
	}

	public List<Refeicao> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Refeicao> criteria = cb.createQuery(Refeicao.class);
		Root<Refeicao> member = criteria.from(Refeicao.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(member).orderBy(cb.asc(member.get("name")));
		return em.createQuery(criteria).getResultList();
	}

	public Refeicao findByEmail(String email) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Refeicao> criteria = cb.createQuery(Refeicao.class);
		Root<Refeicao> member = criteria.from(Refeicao.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).where(cb.equal(member.get(Member_.name),
		// email));
		criteria.select(member).where(cb.equal(member.get("email"), email));
		return em.createQuery(criteria).getSingleResult();
	}

	public Refeicao findById(Long id) {
		return em.find(Refeicao.class, id);
	}
}
