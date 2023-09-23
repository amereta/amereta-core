package tech.amereta.core;

import tech.amereta.core.soy.ISoyConfiguration;

import java.util.List;

public interface SourceCode {

    List<CompilationUnit> getCompilationUnits();

    List<CompilationUnit> getTestCompilationUnits();

    List<ISoyConfiguration> getStaticCompilationUnits();
}
