/**
 * Copyright 2015-2017 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin.storage.elasticsearch.http.integration;

import java.io.IOException;
import org.junit.ClassRule;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import zipkin.storage.elasticsearch.http.ElasticsearchHttpStorage;
import zipkin.storage.elasticsearch.http.InternalForTests;

@RunWith(Enclosed.class)
public class ElasticsearchHttpV2Test {

  @ClassRule
  public static LazyElasticsearchHttpStorage storage =
      new LazyElasticsearchHttpStorage("openzipkin/zipkin-elasticsearch:1.29.1");

  public static class DependenciesTest extends ElasticsearchHttpDependenciesTest {
    @Override protected ElasticsearchHttpStorage storage() {
      return storage.get();
    }
  }

  public static class SpanConsumerTest extends ElasticsearchHttpSpanConsumerTest {
    @Override protected ElasticsearchHttpStorage storage() {
      return storage.get();
    }

    @Override String baseUrl() {
      return storage.baseUrl();
    }
  }

  public static class SpanStoreTest extends zipkin.storage.SpanStoreTest {
    @Override protected ElasticsearchHttpStorage storage() {
      return storage.get();
    }

    @Override public void clear() throws IOException {
      InternalForTests.clear(storage());
    }
  }

  public static class StrictTraceIdFalseTest extends ElasticsearchHttpStrictTraceIdFalseTest {
    @Override protected ElasticsearchHttpStorage.Builder storageBuilder() {
      return ElasticsearchHttpV2Test.storage.computeStorageBuilder();
    }
  }
}
