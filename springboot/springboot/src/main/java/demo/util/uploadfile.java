package demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class uploadfile {


    public String uploadfile(MultipartFile uploadFile,String filename,HttpServletRequest req,String url) {

        //添加日期目录
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String format = sd.format(new Date());
        String name = format + "-" +filename;

        try(InputStream inputStream = uploadFile.getInputStream())
        {
            Path uploadPath = Paths.get(url);
            if(!uploadPath.toFile().exists())
                uploadPath.toFile().mkdirs();
            Files.copy(inputStream, Paths.get(url).resolve(name), StandardCopyOption.REPLACE_EXISTING);

            return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +
                    url.substring(url.lastIndexOf("/")) + "/" + name;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "false";
        }

    }
}
