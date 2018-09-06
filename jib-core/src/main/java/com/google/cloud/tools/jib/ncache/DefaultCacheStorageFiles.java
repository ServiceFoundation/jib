/*
 * Copyright 2018 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.tools.jib.ncache;

import com.google.cloud.tools.jib.image.DescriptorDigest;
import java.nio.file.Path;

/** Resolves the files used in the default cache storage engine. */
class DefaultCacheStorageFiles {

  private static final String LAYERS_DIRECTORY = "layers";
  private static final String LAYER_FILENAME_SUFFIX = ".layer";
  private static final String METADATA_FILENAME = "metadata";
  private static final String SELECTORS_DIRECTORY = "selectors";

  private final Path cacheDirectory;

  DefaultCacheStorageFiles(Path cacheDirectory) {
    this.cacheDirectory = cacheDirectory;
  }

  /**
   * Resolves the layer contents file.
   *
   * @param layerDigest the layer digest
   * @param layerDiffId the layer diff Id
   * @return the layer contents file
   */
  Path getLayerFile(DescriptorDigest layerDigest, DescriptorDigest layerDiffId) {
    return getLayerDirectory(layerDigest).resolve(getLayerFilename(layerDiffId));
  }

  /**
   * Gets the filename for the layer file. The filename is in the form {@code <layer diff
   * ID>.layer}.
   *
   * @param layerDiffId the layer's diff ID
   * @return the layer filename
   */
  String getLayerFilename(DescriptorDigest layerDiffId) {
    return layerDiffId.getHash() + LAYER_FILENAME_SUFFIX;
  }

  /**
   * Resolves the layer metadata file.
   *
   * @param layerDigest the layer digest
   * @return the layer metadata file
   */
  Path getMetadataFile(DescriptorDigest layerDigest) {
    return getLayerDirectory(layerDigest).resolve(METADATA_FILENAME);
  }

  /**
   * Gets the filename for the metadata file.
   *
   * @return the filename for the metadata file
   */
  String getMetadataFilename() {
    return METADATA_FILENAME;
  }

  /**
   * Resolves a selector file.
   *
   * @param selector the selector digest
   * @return the selector file
   */
  Path getSelectorFile(DescriptorDigest selector) {
    return cacheDirectory.resolve(SELECTORS_DIRECTORY).resolve(selector.getHash());
  }

  /**
   * Resolves the layers directory.
   *
   * @return the directory with all the layer directories
   */
  Path getLayersDirectory() {
    return cacheDirectory.resolve(LAYERS_DIRECTORY);
  }

  /**
   * Gets the directory for the layer with digest {@code layerDigest}.
   *
   * @param layerDigest the digest of the layer
   * @return the directory for that {@code layerDigest}
   */
  Path getLayerDirectory(DescriptorDigest layerDigest) {
    return getLayersDirectory().resolve(layerDigest.getHash());
  }
}
