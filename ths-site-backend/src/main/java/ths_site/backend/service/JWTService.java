package ths_site.backend.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ths_site.backend.model.User;

@Service
public class JWTService {
  //@Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.expiration-time}")
  private int jwtExpiration;

  public JWTService() {
    try {
      KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
      SecretKey sk = keyGen.generateKey();
      this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  // -------------------------

  // - Generates the JWT token
  public String generateToken(Authentication auth, User user) {

    // - Generate the claims (payload). It's empty to be later added claims in the
    // return-statement.
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", user.getId());
    claims.put("name", user.getFirstName() + " " + user.getLastName());
    claims.put("email", auth.getName());
    claims.put("authorities", auth.getAuthorities());
    claims.put("userType", user.getClass().getSimpleName().toLowerCase());

    System.out.println(":::" + claims.toString() + ":::");

    return Jwts.builder()
        // Adds the empty claims, later parameters adds to the claims.
        .claims()
        .add(claims)
        .subject(auth.getName())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .and()
        .signWith(getKey())
        .compact();
  }

  // -------------------------

  // - Gets the SecretKey from the String secretKey (application.properties).
  private SecretKey getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // Extact a specific claim (payload) from the token. (See
  // extractUsername(token);)
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // - Extract userEmail from token (username) for users this will be an email.
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }
  // - Extract userEmail from token (username) for users this will be an email.
  public String extractId(String token) {
    return extractClaim(token, Claims::getId);
  }

  // Extact all claims (payload) from the token.
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  // - Checks if the token is valid.
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  // - Checks if the token is expired.
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // Get the expiration date from the token.
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public int getExpirationTime() {
    return jwtExpiration;
  }

}