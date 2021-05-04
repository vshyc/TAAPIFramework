package com.tipico.services.talimitservice;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

@AnalyzeClasses(packages = "com.tipico.services.talimitservice",
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
class CodeSanityTest {

    @ArchTest
    void checkLayers(final JavaClasses javaClasses) {
        layeredArchitecture()
                .withOptionalLayers(true)
                .layer("View").definedBy("..view..")
                .layer("Domain").definedBy("..domain..")
                .layer("Infra").definedBy("..infra..")
                .layer("Common").definedBy("..common..")

                .whereLayer("View").mayNotBeAccessedByAnyLayer()
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("View", "Domain")
                .whereLayer("Infra").mayOnlyBeAccessedByLayers("Domain", "Infra")
                .whereLayer("Common").mayOnlyBeAccessedByLayers("View", "Domain", "Infra", "Common")
                .because("Layers provide better structure")
                .check(javaClasses);
    }

    @ArchTest
    void checkCyclesInCode(JavaClasses javaClasses) {
        slices().matching("com.tipico.services.talimitservice.(*)..")
                .should()
                .beFreeOfCycles()
                .check(javaClasses);
    }

    @ArchTest
    void checkSomeStandardCodingRules(JavaClasses javaClasses) {
        NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(javaClasses);
        NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS
                .because("Don't use stdout or stderr. Use a logger instead")
                .check(javaClasses);
    }

    @ArchTest
    void checkBeansAreInAppropriatePackage(JavaClasses javaClasses) {
        classes().that().haveNameMatching(".*Bean").or().haveNameMatching(".*Impl")
                .should().notBeInterfaces().andShould().resideInAPackage("..impl..")
                .check(javaClasses);
    }
}