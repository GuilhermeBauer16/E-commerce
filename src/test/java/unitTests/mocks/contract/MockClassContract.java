package unitTests.mocks.contract;

import java.util.List;
import java.util.UUID;

public interface MockClassContract<M,V> {
    // M - Model
    // V - VO(Value Object)


    public M mockMEntity();

    public V mockVVO();

    public M mockMEntity(UUID uuid);

    public V mockVVO(UUID uuid);


    public List<M> mockMEntityList();

    public List<V> mockVVOList();
}

