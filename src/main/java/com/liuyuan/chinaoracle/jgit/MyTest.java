package com.liuyuan.chinaoracle.jgit;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class MyTest {

    public static void main(String[] args) throws IOException {
        Repository repository = FileRepositoryBuilder.create(
            new File("/temp" + "/my_repo/.git"));
        new FileRepositoryBuilder().setGitDir(
                new File("my_repo/.git"))
            .build();
    }
}
