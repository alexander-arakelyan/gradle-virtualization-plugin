package io.github.bambrikii.gradle.virtualization.plugin.utils;

import io.github.bambrikii.gradle.virtualization.plugin.extensions.KubernetesExtension;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtensionContainer;

public class KubernetesTaskUtils {
  private KubernetesTaskUtils() {
  }

  public static NamedDomainObjectContainer<KubernetesExtension> registerKubernetesExtension(ExtensionContainer extensions, ObjectFactory objects) {
    NamedDomainObjectContainer<KubernetesExtension> kubernetes = objects.domainObjectContainer(
            KubernetesExtension.class,
            name -> objects.newInstance(KubernetesExtension.class, name)
    );
    extensions.add("kubernetes", kubernetes);
    return kubernetes;
  }
}
