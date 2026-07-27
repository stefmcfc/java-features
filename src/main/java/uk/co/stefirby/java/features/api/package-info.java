/**
 * A thin Spring Boot 4.1 REST layer exposing selected examples over HTTP.
 * Controllers here stay thin and delegate straight to the static methods in
 * the topic packages (e.g. {@code streams}, {@code optional}) so every
 * feature stays runnable both as a console demo and as an endpoint.
 */
package uk.co.stefirby.java.features.api;
