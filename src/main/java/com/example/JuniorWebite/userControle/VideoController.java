package com.example.JuniorWebite.userControle;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class VideoController {

    @Autowired
    private ResourceLoader resourceLoader;

    private final String[] directories = {
            "C:/Users/oussa/Desktop/projets/Loopra/stories/",
            "C:/Users/oussa/Desktop/projets/Loopra/posts/"
    };

    @GetMapping("/videos/{filename:.+}")
    public ResponseEntity<Resource> getVideo(@PathVariable String filename) {
        for (String directory : directories) {
            try {
                Resource resource = resourceLoader.getResource("file:" + directory + filename);
                if (resource.exists()) {
                    return ResponseEntity.ok().body(resource);
                }
            } catch (Exception e) {
                // Gérer l'erreur si nécessaire
            }
        }
        return ResponseEntity.notFound().build(); // Si aucune vidéo n'a été trouvée
    }
}
