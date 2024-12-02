package com.example.JuniorWebite.userService;
import com.example.JuniorWebite.Dto.PostResponse;
import com.example.JuniorWebite.Dto.StoryResponse;
import com.example.JuniorWebite.Entity.Story;
import com.example.JuniorWebite.userRepository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    private final String uploadDir = "C:/Users/oussa/Desktop/projets/Loopra/stories/"; // Dossier local pour stocker les vidéos

    // Constructeur

    public StoryService() {
        // Créer le dossier si cela n'existe pas
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'erreur de création de dossier
        }
    }

    // Méthode pour créer une story
    public StoryResponse createStory(Story story, MultipartFile file) {
        StoryResponse storyResponse = new StoryResponse();

        try {
            if (file != null) {
                // Vérifier si le fichier existe déjà
                String filePath = uploadDir + file.getOriginalFilename();
                File localFile = new File(filePath);
                if (localFile.exists()) {
                    storyResponse.setState("failed");
                    storyResponse.setMessage("Le nom du fichier existe déjà. Veuillez changer le nom du fichier vidéo.");
                    storyResponse.setData(null); // Pas de données à retourner
                    return storyResponse;
                }
                // Enregistrer le fichier sur le serveur
                file.transferTo(localFile); // Enregistrer le fichier
                story.setVideoPath(filePath); // Stocker le chemin du fichier
            }

            // Sauvegarder la story dans la base de données
            Story savedStory = storyRepository.save(story);
            storyResponse.setState("success");
            storyResponse.setMessage("Story créée avec succès.");
            storyResponse.setData(null); // Retourner la story créée

        } catch (IOException e) {
            storyResponse.setState("failed");
            storyResponse.setMessage("Erreur lors de la création de la story : " + e.getMessage());
            storyResponse.setData(null); // Pas de données à retourner
        }

        return storyResponse;
    }

    // Méthode pour obtenir toutes les stories
    public StoryResponse getAllStories() {
        StoryResponse storyResponse = new StoryResponse();
        List<Story> stories = storyRepository.findAll();

        if (stories.isEmpty()) {
            storyResponse.setState("failed");
            storyResponse.setMessage("Aucune story trouvée.");
            storyResponse.setData(null);
        } else {
            storyResponse.setState("success");
            storyResponse.setMessage("Stories récupérées avec succès.");
            storyResponse.setData(stories); // Retourner toutes les stories
        }
        return storyResponse;
    }

    // Méthode pour obtenir les stories par ID d'utilisateur
    public StoryResponse getStoriesByUserId(int userId) {
        StoryResponse storyResponse = new StoryResponse();
        List<Story> stories = storyRepository.findByUser_UserID(userId); // Assurez-vous d'avoir cette méthode dans le repository

        if (stories.isEmpty()) {
            storyResponse.setState("failed");
            storyResponse.setMessage("Aucune story trouvée pour cet utilisateur.");
            storyResponse.setData(null); // Pas de données à retourner
        } else {
            storyResponse.setState("success");
            storyResponse.setMessage("Stories récupérées avec succès pour l'utilisateur ID " + userId + ".");
            storyResponse.setData(stories); // Retourner les stories trouvées
        }

        return storyResponse;
    }
    public StoryResponse getStoryById(int id) {
        StoryResponse storyResponse = new StoryResponse();

        // Rechercher une Story par son ID
        Story story = storyRepository.findById(id);

        if (story != null) {
            // Créer une liste pour contenir la story
            ArrayList<Story> listOfStory = new ArrayList<>();
            listOfStory.add(story);

            // Ajouter la liste au StoryResponse
            storyResponse.setData(listOfStory);
        } else {
            // Gérer le cas où aucune story n'est trouvée
            storyResponse.setData(new ArrayList<>()); // Liste vide
        }

        return storyResponse;
    }

}

