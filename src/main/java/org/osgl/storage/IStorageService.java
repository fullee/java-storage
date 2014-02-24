/* 
 * Copyright (C) 2013 The Java Storage project
 * Gelin Luo <greenlaw110(at)gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.osgl.storage;

import org.osgl._;
import org.osgl.exception.UnexpectedIOException;

import java.util.Map;


/**
 * Define A storage service
 */
public interface IStorageService {

    public static final String CONF_KEY_GEN = "storage.keygen";

    /**
     * Configure the service
     * 
     * @param conf
     */
    void configure(Map<String, String> conf);
    
    /**
     * Retrieve the stuff from the storage by key
     * 
     * If file cannot be find by key, then <code>null</code> is returned
     * 
     * @param key
     * @return the file associated with key or null if not found
     */
    ISObject get(String key);

    /**
     * Returns lazy SObject with attributes set. This method is useful when user
     * application choose to store attributes and key of SObject into database, and
     * lazy load SObject content later one by calling {@link #loadContent(ISObject)}
     *
     * @param key
     * @param attrs
     * @return a sobject with meta attributes and key only. content of the sobject is not loaded
     */
    ISObject getLazy(String key, Map<String, String> attrs);

    /**
     * Force retrieving the stuff with content from storage without regarding to the configuration
     * 
     * @param key
     * @return the storage stuff
     * @deprecated
     * @see #getFull(String)
     */
    ISObject forceGet(String key);

    /**
     * Force retrieving the stuff with content from storage without regarding to the configuration
     *
     * @param key
     * @return the storage stuff
     * @see #loadContent(ISObject)
     */
    ISObject getFull(String key);


    /**
     * Load content of an sobject. If the content is already loaded, then the object should
     * be returned directly
     *
     * @param sobj
     * @return the sobject with content presented
     */
    ISObject loadContent(ISObject sobj);
    
    /**
     * Update the stuff in the storage. If the existing file cannot be find
     * in the storage then it will be added.
     * 
     * @param key
     * @param stuff
     */
    void put(String key, ISObject stuff) throws UnexpectedIOException;
    
    /**
     * Remove the file from the storage by key and return it to caller.
     * 
     * @param key
     */
    void remove(String key);

    /**
     * Return the URL to access a stored resource by key 
     * 
     * @param key
     * @return the URL
     */
    String getUrl(String key);
    
    String getKey(String key);
    
    public String getKey();
    
    public static class f {
        public static _.F0<Void> put(final String key, final ISObject stuff, final IStorageService ss) {
            return put().curry(key, stuff, ss);
        }

        public static _.F3<String, ISObject, IStorageService, Void> put() {
            return new _.F3<String, ISObject, IStorageService, Void>() {
                @Override
                public Void apply(String s, ISObject isObject, IStorageService iStorageService) {
                    iStorageService.put(s, isObject);
                    return null;
                }
            };
        }

        public static _.F0<ISObject> get(final String key, IStorageService ss) {
            return get().curry(key, ss);
        }
        
        public static _.F2<String, IStorageService, ISObject> get() {
            return new _.F2<String, IStorageService, ISObject>() {
                @Override
                public ISObject apply(String key, IStorageService ss) {
                    return ss.get(key);
                }
            };
        }

        public static _.F0<Void> remove(final String key, IStorageService ss) {
            return remove().curry(key, ss);
        }
        
        public static _.F2<String, IStorageService, Void> remove() {
            return new _.F2<String, IStorageService, Void>() {
                @Override
                public Void apply(String s, IStorageService ss) {
                    ss.remove(s);
                    return null;
                }
            };
        }
    }
}