package cky.cky_api.service.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataResult<T> extends CommonResult {
    private T result;
}
