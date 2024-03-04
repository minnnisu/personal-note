package minnnisu.personalnote;

import minnnisu.personalnote.constant.UploadPathType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class PersonalNoteApplication {

    public static void main(String[] args) {

        deleteFilesAndSubfolders(new File(UploadPathType.IMAGE.getPath()));
        SpringApplication.run(PersonalNoteApplication.class, args);
    }

    private static void deleteFilesAndSubfolders(File folder) {
        // 폴더 내 모든 파일 및 폴더 리스트 가져오기
        File[] files = folder.listFiles();

        if (files != null) {
            // 폴더 내 파일 및 하위 폴더에 대해 반복하여 삭제
            for (File file : files) {
                if (file.isDirectory()) {
                    // 하위 폴더인 경우 재귀적으로 삭제 메서드 호출
                    deleteFilesAndSubfolders(file);
                } else {
                    // 파일인 경우 삭제
                    file.delete();
                }
            }
        }
    }

}
