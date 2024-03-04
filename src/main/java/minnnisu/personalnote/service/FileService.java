package minnnisu.personalnote.service;

import lombok.extern.slf4j.Slf4j;
import minnnisu.personalnote.constant.UploadPathType;
import minnnisu.personalnote.util.UniqueFileNameGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@Slf4j
public class FileService {
    public boolean checkImageFile(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().startsWith("image");
    }

    public String saveNoteImage(MultipartFile file) throws Exception {
        String uniqueFileName = UniqueFileNameGenerator.generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(UploadPathType.IMAGE.getPath() + "/" + uniqueFileName);
        Files.copy(file.getInputStream(), filePath);
        return uniqueFileName;
    }

    public void onSaveNoteFailure(String imageName) {
        String filePath = UploadPathType.IMAGE.getPath() + "/" + imageName;
        File file = new File(filePath);
        if (!file.delete()) {
            log.info("노트 이미지 파일의 삭제에 실패하였습니다.");
            log.info("이미지 위치:" + filePath);
        }
    }
}
