public class Test {

    //static MiraiLogger logger = MiraiLogger.create(DownloadUtil.class.getName());

    public static void main(String[] args) {
//        String url = "https://www.baidu.com/asd.asda/asd /aps.jav";
//        String name = url.substring(url.lastIndexOf("/"));
//        System.out.println(name);

//        String[] regexs = {"[setu]"};
//        //boolean flag = "pid1564946".matches(regexs);
//        boolean flag = false;
//        for(String reg : regexs){
//            flag = "setu".matches(reg);
//            System.out.println(flag);
//            if(flag){
//                break;
//            }
//        }

//        System.out.println(flag);
        //System.out.println(Boolean.valueOf("TRUE"));

//            String url = "https://api.lolicon.app/setu/v2?tag=loli&num=3&r18=1";
//
//            URL uri = new URL(url);
//            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
//
//            connection.setConnectTimeout(5000);
//            connection.setInstanceFollowRedirects(false);
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            connection.setRequestMethod("GET");
//
//            connection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36");
//
//            //OutputStream outStream = connection.getOutputStream();
//            //outStream.write(param.getBytes(StandardCharsets.UTF_8));
//            //outStream.flush();
//
//            connection.connect();
//
//            int responseCode = connection.getResponseCode();
//
//            System.out.println(responseCode);
//
//            InputStream inputStream = null;
//            inputStream = connection.getInputStream();//会隐式调用connect()
//
//            StringBuilder stringBuilder = new StringBuilder();
//            byte[] by = new byte[2048];
//            int tem;
//            while((tem = inputStream.read(by)) != -1){
//                stringBuilder.append(new String(by, 0, tem, StandardCharsets.ISO_8859_1));
//            }
//            inputStream.close();
//
//            System.out.println(stringBuilder);

//            ThreadPoolExecutor downloadExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
//            Future<String> downloadres = null;
//
//            ThreadPoolExecutor uploadExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
//                    new ArrayBlockingQueue<>(5));
//
//            String apiurl = Constants.LOLICON_URL + "&num=5";
//            String json = "";
//            try {
//                json = IOUtil.readInputStream(DownloadUtil.openUrl(apiurl, "GET"));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            Map<String, Object> map = JSONUtil.str2json(json);
//            JSONArray data = (JSONArray) map.get("data");

//            for(Object obj : data){
//                String url = ((JSONObject) ((JSONObject) obj).get("urls")).get("original").toString();
//                url = url.replace(Constants.PIXIV_CAT_PERFIX, Constants.PROXY_PIXIV_PERFIX);
//                downloadres = downloadExecutor.submit(new DownloadTask(url));
//            }
//
//            while (downloadExecutor.getTaskCount() > 0){
//                try {
//                    System.out.println(downloadres.get());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//         logger.info("任务已完成...");

//        String rootpath = "/root/app/mcl/download/data/image/";
//        File root = new File(rootpath);
//        for (File img : Objects.requireNonNull(root.listFiles())){
//            String fileName = img.getName();
//
//            String path = fileName.split("_")[0];
//            File dir = new File(rootpath + path);
//            if (!dir.exists()){
//                dir.mkdirs();
//            }
//
//            try {
//                InputStream inputStream = new FileInputStream(img);
//                FileOutputStream outputStream = new FileOutputStream(rootpath + fileName.replace("_", "/"));
//                int len;
//                byte[] bytes = new byte[2048];
//                while ((len = inputStream.read(bytes)) != -1) {
//                    outputStream.write(bytes, 0, len);
//                }
//                inputStream.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }


//        String text = "来3张abc图";
//        String reg = "来[1-9](.*)[色|涩]图";
//
//        System.out.println(text.matches(reg));
//        System.out.println(text.charAt(1));
//        System.out.println(text.substring(3, text.length()-1));


//        String apiurl = "https://api.lolicon.app/setu/v2?tag=loli";
//
//        String json = null;
//        try {
//            json = IOUtil.readInputStream(DownloadUtil.openUrl(apiurl, "GET", ""));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Map<String, Object> map = JSONUtil.str2json(json);
//        JSONArray data = (JSONArray) map.get("data");
//        for (Object obj : data) {
//            String url = ((JSONObject) ((JSONObject) obj).get("urls")).get("original").toString();
//            url = url.replace(Constants.PIXIV_CAT_PERFIX, Constants.PROXY_PIXIV_PERFIX);
//
//            // DownloadTask downloadTask = new DownloadTask(url);
//            // downloadres = downloadExecutor.submit(downloadTask);
//
//            // String localurl = downloadres.get();
//            // UploadImageTask uploadTask = new UploadImageTask(event, localurl);
//            // Future<Image> uploadres = uploadExecutor.submit(uploadTask);
//            // Image image = uploadres.get();
//            // messages.add(image);
//
//            try {
//                String localpath = DownloadUtil.saveFile(url, "GET", "");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // ExternalResource externalResource = ExternalResource.create(new FileInputStream(localpath));
//            // Image image = event.getSubject().uploadImage(externalResource);
//            // externalResource.close();
//            // messages.add(image);
//        }

//        String str = "nmd,,,,,wsm,,,,yyds";
//        System.out.println(str.replaceAll(",+", ","));

//        String str = "8张阿婆说的a三李四d王五D赵六D";
//        System.out.println(str.replaceAll("[^\\w]+", ","));
//        System.out.println(str.replaceAll("[^\\w]+", ",").substring(1));
//
//
//        try {
//            String text = "张三李四王五赵六钱七";
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            BigInteger digest = new BigInteger(md5.digest(text.getBytes()));
//            //32位
//            String de = digest.toString(16);
//            System.out.println(de);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
        System.out.println("说123".matches("说.*"));

    }

}
