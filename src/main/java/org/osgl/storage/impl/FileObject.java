package org.osgl.storage.impl;

/*-
 * #%L
 * Java Storage Service
 * %%
 * Copyright (C) 2013 - 2017 OSGL (Open Source General Library)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.osgl.storage.ISObject;

class FileObject extends StorageObject<FileObject, FileSystemService> {

    FileObject(String key, FileSystemService fileSystemService) {
        super(key, fileSystemService);
        buf(); // eager load buf
    }

    @Override
    public long getLength() {
        return buf().getLength();
    }

    @Override
    protected ISObject loadBuf() {
        String fullPath = svc.keyWithContextPath(getKey());
        return SObject.of(fullPath, svc.getFile(fullPath));
    }
}
