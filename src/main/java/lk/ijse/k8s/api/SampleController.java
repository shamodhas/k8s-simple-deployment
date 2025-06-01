package lk.ijse.k8s.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 6/1/2025 8:04 AM
 * Project: k8s-simple-deployment
 * --------------------------------------------
 **/

@RestController
@RequestMapping("/api/v1/app")
public class SampleController {

    @GetMapping("/sample")
    public String get() {
        return "Sample controller response";
    }
}
