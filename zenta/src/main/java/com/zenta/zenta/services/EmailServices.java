package com.zenta.zenta.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.zenta.zenta.entity.User;

@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo de bienvenida con los datos de acceso.
     * @param user usuario recién registrado
     */
    public void enviarCorreoBienvenida(User user) {
        String asunto = "¡Bienvenido a Zenta!";
        String loginUrl = "http://localhost:8090/"; // Cambia esto por tu URL real

        // ⚠️ Si usas contraseñas encriptadas, evita incluirlas en el correo
        String contenidoHtml = """
        <html>
            <body style="font-family: Arial, sans-serif;">
                <h2>Hola %s, ¡Bienvenido a Zenta!</h2>
                <p>Nos complace informarte que ya hemos creado tu cuenta en Zenta. A continuación, encontrarás tus credenciales de acceso:</p>

        		<strong>Correo:</strong> %s
                <strong>Contraseña:</strong> %s

                <p>Para acceder a tu cuenta, simplemente haz clic en el siguiente botón:</p>
                <a href="%s"
                   style="display:inline-block;padding:10px 20px;background-color:#1a1a1a;
                          color:white;text-decoration:none;border-radius:5px;">
                   Iniciar sesión
                </a>
                <p>Si tienes alguna pregunta o necesitas asistencia, no dudes en contactarnos. ¡Estamos aquí para ayudarte!</p>
                <p>Atte: El equipo de Zenta</p>
                <br>
                <small>Este es un mensaje automático, por favor no responder.</small>
            </body>
        </html>
        """.formatted(
                user.getNombre() + " " + user.getApellido(),
                user.getEmail(),
                user.getContrasena(),
                loginUrl
        );

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true); // true → indica HTML

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}
