/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.wayang.commons.util.profiledb.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import org.apache.wayang.commons.util.profiledb.model.Experiment;

public class FileStorage extends Storage {

    /**
     * File where {@link Experiment}s will be written
     */
    private File file;

    /**
     * Assigns File where {@link Experiment}s will be written regarding to given URI
     *
     * @param uri URI where experiments are persisted
     */
    public FileStorage(URI uri) {

        super(uri);
        this.file = new File(uri);
    }

    /**
     * To change target URI during execution
     *
     * @param uri determines new URI where {@link Experiment}s will be persisted
     */
    @Override
    public void changeLocation(URI uri){

        super.changeLocation(uri);
        this.file = new File(uri);
    }

    /**
     * Write {@link Experiment}s to a {@link File}. Existing file contents will be overwritten.
     *
     * @param experiments the {@link Experiment}s
     * @throws IOException if the writing fails
     */
    @Override
    public void save(Collection<Experiment> experiments) throws IOException {
        this.file.getAbsoluteFile().getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(this.file, false)) {
            this.save(experiments, fos);
        }
    }

    /**
     * Write {@link Experiment}s to a {@link File}. Existing file contents will be overwritten.
     *
     * @param experiments the {@link Experiment}s
     * @throws IOException if the writing fails
     */
    @Override
    public void save(Experiment... experiments) throws IOException {
        this.save(Arrays.asList(experiments));
    }

    /**
     * Load {@link Experiment}s from a {@link File}.
     *
     * @return the {@link Experiment}s
     */
    @Override
    public Collection<Experiment> load() throws IOException {
        return load(new FileInputStream(this.file));
    }

    /**
     * Append {@link Experiment}s to a {@link File}. Existing file contents will be preserved.
     *
     * @param experiments the {@link Experiment}s
     * @throws IOException if the writing fails
     */
    @Override
    public void append(Collection<Experiment> experiments) throws IOException {
        this.file.getAbsoluteFile().getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(this.file, true)) {
            this.save(experiments, fos);
        }
    }

    /**
     * Append {@link Experiment}s to a {@link File}. Existing file contents will be preserved.
     *
     * @param experiments the {@link Experiment}s
     * @throws IOException if the writing fails
     */
    @Override
    public void append(Experiment... experiments) throws IOException {
        this.append(Arrays.asList(experiments));
    }

}
