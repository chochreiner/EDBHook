/**
 * Licensed to the Austrian Association for Software Tool Integration (AASTI)
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. The AASTI licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.framework.edb.hooks.internal;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.openengsb.core.api.edb.EDBObject;
import org.openengsb.similarity.AbstractIndex;

public class PLCCombinedIndex extends AbstractIndex {

    public PLCCombinedIndex() throws IOException {
        super("data/similarity/plccombined");
    }

    @Override
    protected void addDocument(EDBObject content) throws IOException {
        Document doc = new Document();
        doc.add(new Field("combinedkey", buildDocumentStructure(content), Field.Store.YES, Field.Index.NOT_ANALYZED));
        this.writer.updateDocument(new Term("oid", content.getOID()), doc);
    }

    private String buildDocumentStructure(EDBObject content) {
        StringBuilder document = new StringBuilder();
        document.append(content.get("region").toString());
        document.append(content.get("kks0").toString());
        document.append(content.get("kks1").toString());
        document.append(content.get("kks2").toString());
        document.append(content.get("kks3").toString());
        return document.toString();
    }

    @Override
    protected String buildQueryString(EDBObject sample) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("combinedkey:");
        queryString.append(sample.getString("region"));
        queryString.append(sample.getString("kks0"));
        queryString.append(sample.getString("kks1"));
        queryString.append(sample.getString("kks2"));
        queryString.append(sample.getString("kks3"));
        return queryString.toString();
    }
}
