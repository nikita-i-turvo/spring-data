/*
 * DISCLAIMER
 *
 * Copyright 2016 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package com.arangodb.springframework.core.template;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.ArangoDBVersion;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.DocumentDeleteEntity;
import com.arangodb.entity.DocumentImportEntity;
import com.arangodb.entity.DocumentUpdateEntity;
import com.arangodb.entity.EdgeEntity;
import com.arangodb.entity.EdgeUpdateEntity;
import com.arangodb.entity.MultiDocumentEntity;
import com.arangodb.entity.VertexEntity;
import com.arangodb.entity.VertexUpdateEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.model.DocumentCreateOptions;
import com.arangodb.model.DocumentDeleteOptions;
import com.arangodb.model.DocumentImportOptions;
import com.arangodb.model.DocumentReadOptions;
import com.arangodb.model.DocumentReplaceOptions;
import com.arangodb.model.DocumentUpdateOptions;
import com.arangodb.model.EdgeCreateOptions;
import com.arangodb.model.EdgeDeleteOptions;
import com.arangodb.model.EdgeReplaceOptions;
import com.arangodb.model.EdgeUpdateOptions;
import com.arangodb.model.VertexCreateOptions;
import com.arangodb.model.VertexDeleteOptions;
import com.arangodb.model.VertexReplaceOptions;
import com.arangodb.model.VertexUpdateOptions;
import com.arangodb.springframework.core.ArangoOperations;
import com.arangodb.springframework.core.convert.ArangoConverter;
import com.arangodb.springframework.core.util.ArangoExceptionTranslator;
import com.arangodb.velocypack.VPackBuilder;

/**
 * @author Mark - mark at arangodb.com
 *
 */
public class ArangoTemplate implements ArangoOperations {

	private final PersistenceExceptionTranslator exceptionTranslator;
	private final ArangoConverter converter;
	private final ArangoDB arango;
	private final String database;

	public ArangoTemplate(final ArangoDB.Builder arango, final String database) {
		this(arango, database, null);
	}

	public ArangoTemplate(final ArangoDB.Builder arango, final String database, final ArangoConverter converter) {
		this(arango, database, converter, new ArangoExceptionTranslator());
	}

	public ArangoTemplate(final ArangoDB.Builder arango, final String database, final ArangoConverter converter,
		final PersistenceExceptionTranslator exceptionTranslator) {
		super();
		this.arango = arango.build();
		this.database = database;
		this.converter = converter;
		this.exceptionTranslator = exceptionTranslator;
	}

	private DataAccessException translateExceptionIfPossible(final RuntimeException exception) {
		return exceptionTranslator.translateExceptionIfPossible(exception);
	}

	private String determineCollectionName(final Class<?> entityClass) {
		return converter.getMappingContext().getPersistentEntity(entityClass)
				.orElseThrow(() -> new InvalidDataAccessApiUsageException(
						"No persistent entity information found for the type " + entityClass.getName()))
				.getCollection();
	}

	@Override
	public ArangoDB driver() {
		return arango;
	}

	@Override
	public ArangoDBVersion getVersion() throws DataAccessException {
		try {
			return arango.getVersion();
		} catch (final ArangoDBException e) {
			throw translateExceptionIfPossible(e);
		}
	}

	@Override
	public <T> ArangoCursor<T> query(
		final String query,
		final Map<String, Object> bindVars,
		final AqlQueryOptions options,
		final Class<T> type) throws DataAccessException {
		return null;
	}

	@Override
	public <T> MultiDocumentEntity<DocumentDeleteEntity<T>> deleteDocuments(
		final Collection<?> values,
		final Class<T> type,
		final DocumentDeleteOptions options) throws DataAccessException {
		return null;
	}

	@Override
	public MultiDocumentEntity<DocumentDeleteEntity<Void>> deleteDocuments(final Collection<?> values)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> DocumentDeleteEntity<T> deleteDocument(
		final String key,
		final Class<T> type,
		final DocumentDeleteOptions options) throws DataAccessException {
		return null;
	}

	@Override
	public DocumentDeleteEntity<Void> deleteDocument(final String key) throws DataAccessException {
		return null;
	}

	@Override
	public <T> MultiDocumentEntity<DocumentUpdateEntity<T>> updateDocuments(
		final Collection<T> values,
		final DocumentUpdateOptions options) throws DataAccessException {
		return null;
	}

	@Override
	public <T> MultiDocumentEntity<DocumentUpdateEntity<T>> updateDocuments(final Collection<T> values)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> DocumentUpdateEntity<T> updateDocument(
		final String key,
		final T value,
		final DocumentUpdateOptions options) throws DataAccessException {
		return null;
	}

	@Override
	public <T> DocumentUpdateEntity<T> updateDocument(final String key, final T value) throws DataAccessException {
		return null;
	}

	@Override
	public <T> MultiDocumentEntity<DocumentUpdateEntity<T>> replaceDocuments(
		final Collection<T> values,
		final DocumentReplaceOptions options) throws DataAccessException {
		return null;
	}

	@Override
	public <T> MultiDocumentEntity<DocumentUpdateEntity<T>> replaceDocuments(final Collection<T> values)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> DocumentUpdateEntity<T> replaceDocument(
		final String key,
		final T value,
		final DocumentReplaceOptions options) throws DataAccessException {
		return null;
	}

	@Override
	public <T> DocumentUpdateEntity<T> replaceDocument(final String key, final T value) throws DataAccessException {
		return null;
	}

	@Override
	public <T> T getDocument(final String key, final Class<T> type, final DocumentReadOptions options)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> T getDocument(final String key, final Class<T> type) throws DataAccessException {
		return null;
	}

	@Override
	public DocumentImportEntity importDocuments(final String values, final DocumentImportOptions options)
			throws DataAccessException {
		return null;
	}

	@Override
	public DocumentImportEntity importDocuments(final String values) throws DataAccessException {
		return null;
	}

	@Override
	public DocumentImportEntity importDocuments(final Collection<?> values, final DocumentImportOptions options)
			throws DataAccessException {
		return null;
	}

	@Override
	public DocumentImportEntity importDocuments(final Collection<?> values) throws DataAccessException {
		return null;
	}

	@Override
	public <T> MultiDocumentEntity<DocumentCreateEntity<T>> insertDocuments(
		final Collection<T> values,
		final DocumentCreateOptions options) throws DataAccessException {
		return null;
	}

	@Override
	public <T> MultiDocumentEntity<DocumentCreateEntity<T>> insertDocuments(final Collection<T> values)
			throws DataAccessException {
		return null;
	}

	@Override
	public DocumentCreateEntity<Object> insertDocument(final Object value, final DocumentCreateOptions options)
			throws DataAccessException {
		try {
			final VPackBuilder builder = new VPackBuilder();
			converter.write(value, builder);
			return arango.db(database).collection(determineCollectionName(value.getClass()))
					.insertDocument(builder.slice());
		} catch (final ArangoDBException e) {
			throw exceptionTranslator.translateExceptionIfPossible(e);
		}
	}

	@Override
	public DocumentCreateEntity<Object> insertDocument(final Object value) throws DataAccessException {
		return insertDocument(value, new DocumentCreateOptions());
	}

	@Override
	public void deleteVertex(final String key, final VertexDeleteOptions options) throws DataAccessException {
	}

	@Override
	public void deleteVertex(final String key) throws DataAccessException {
	}

	@Override
	public <T> VertexUpdateEntity updateVertex(final String key, final T value, final VertexUpdateOptions options)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> VertexUpdateEntity updateVertex(final String key, final T value) throws DataAccessException {
		return null;
	}

	@Override
	public <T> VertexUpdateEntity replaceVertex(final String key, final T value, final VertexReplaceOptions options)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> VertexUpdateEntity replaceVertex(final String key, final T value) throws DataAccessException {
		return null;
	}

	@Override
	public <T> T getVertex(final String key, final Class<T> type, final DocumentReadOptions options)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> T getVertex(final String key, final Class<T> type) throws DataAccessException {
		return null;
	}

	@Override
	public <T> VertexEntity insertVertex(final T value, final VertexCreateOptions options) throws DataAccessException {
		return null;
	}

	@Override
	public <T> VertexEntity insertVertex(final T value) throws DataAccessException {
		return null;
	}

	@Override
	public void deleteEdge(final String key, final EdgeDeleteOptions options) throws DataAccessException {
	}

	@Override
	public void deleteEdge(final String key) throws DataAccessException {
	}

	@Override
	public <T> EdgeUpdateEntity updateEdge(final String key, final T value, final EdgeUpdateOptions options)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> EdgeUpdateEntity updateEdge(final String key, final T value) throws DataAccessException {
		return null;
	}

	@Override
	public <T> EdgeUpdateEntity replaceEdge(final String key, final T value, final EdgeReplaceOptions options)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> EdgeUpdateEntity replaceEdge(final String key, final T value) throws DataAccessException {
		return null;
	}

	@Override
	public <T> T getEdge(final String key, final Class<T> type, final DocumentReadOptions options)
			throws DataAccessException {
		return null;
	}

	@Override
	public <T> T getEdge(final String key, final Class<T> type) throws DataAccessException {
		return null;
	}

	@Override
	public <T> EdgeEntity insertEdge(final T value, final EdgeCreateOptions options) throws DataAccessException {
		return null;
	}

	@Override
	public <T> EdgeEntity insertEdge(final T value) throws DataAccessException {
		return null;
	}

}
