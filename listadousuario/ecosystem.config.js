module.exports = {
    apps: [
      {
        name: "apiuser",
        script: "java",
        args: "-jar /home/alopez/web/api.usr.albertolopma.top/public_html/target/listadousuario-0.0.1-SNAPSHOT.jar",
        interpreter: "/bin/sh",
        autorestart: true,
        watch: false,
        max_memory_restart: "1G",
        log_date_format: "YYYY-MM-DD HH:mm Z",
        error_file: "./logs/apiuser-error.log",
        out_file: "./logs/apiuser-out.log",
        merge_logs: true
      }
    ]
  };