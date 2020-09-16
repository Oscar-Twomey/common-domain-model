package cdm.legalagreement.csa.processor;

import com.regnosys.rosetta.common.translation.MappingContext;
import com.regnosys.rosetta.common.translation.Path;
import com.rosetta.model.lib.path.RosettaPath;

import java.util.List;

public class SecurityProviderRightsEventMappingProcessor extends org.isda.cdm.processor.SecurityProviderRightsEventMappingProcessor {

    public SecurityProviderRightsEventMappingProcessor(RosettaPath modelPath, List<Path> synonymPaths, MappingContext mappingContext) {
        super(modelPath, synonymPaths, mappingContext);
    }
}
