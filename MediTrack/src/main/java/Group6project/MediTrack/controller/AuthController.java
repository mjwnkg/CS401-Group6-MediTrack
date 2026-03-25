package Group6project.MediTrack.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Group6project.MediTrack.model.AppUser;
import Group6project.MediTrack.repo.AppUserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.Optional;

@Controller
public class AuthController {

    private final AppUserRepository appUserRepository;

    public AuthController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // ── Login page ────────────────────────────────────────────────────────────

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error != null)  model.addAttribute("error",  "Invalid username or password.");
        if (logout != null) model.addAttribute("message", "You have been logged out.");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Optional<AppUser> userOpt = appUserRepository.findByUsername(username.trim());

        if (userOpt.isEmpty() || !userOpt.get().getPasswordHash().equals(hash(password))) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }

        session.setAttribute("loggedInUser", userOpt.get().getUsername());
        return "redirect:/";
    }

    // ── Logout ────────────────────────────────────────────────────────────────

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    // ── Register page ─────────────────────────────────────────────────────────

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           HttpSession session,
                           Model model) {

        String trimmed = username.trim();

        if (trimmed.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Username and password are required.");
            return "register";
        }
        if (trimmed.length() < 3 || trimmed.length() > 64) {
            model.addAttribute("error", "Username must be between 3 and 64 characters.");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }
        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters.");
            return "register";
        }
        if (appUserRepository.existsByUsername(trimmed)) {
            model.addAttribute("error", "Username already taken.");
            return "register";
        }

        AppUser newUser = new AppUser(trimmed, hash(password));
        appUserRepository.save(newUser);

        session.setAttribute("loggedInUser", trimmed);
        return "redirect:/";
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * SHA-256 hash of the password. For a production app, use BCrypt instead.
     */
    private String hash(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(plain.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
