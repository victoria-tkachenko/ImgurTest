package com.geekbrains;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты фунциональности HelloWorld")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HelloWorldTest {

    private HelloWorld helloWorld;

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(1,2,3),
                Arguments.of(2,2,4)
        );
    }

    @BeforeEach
    void setUp() {
        helloWorld = new HelloWorld();
    }

    @Test
    @Order(1)
    @DisplayName("Тест функции foo")
    void testFoo() {
        assertEquals(1, helloWorld.foo());
    }

    @Order(2)
    @DisplayName("Тесты для суммы двух чисел")
    @ParameterizedTest(name = "Тест №{index}. {0} + {1} = {2}")
    @MethodSource ("data")
    void testSum(int a, int b, int expected) {
        assertEquals(expected, helloWorld.sum(a,b));
    }


    @Order(3)
    @Test
    @DisplayName("Тест деление на 0")
    void testThrowException() {
        assertThrows(
                ArithmeticException.class,
                () -> helloWorld.badFoo(3)
        );
    }

    @Order(4)
    @Test
    @DisplayName("Тест на скорость и качесто сортировки")
    void testArraySorted() {
        assertTimeoutPreemptively(Duration.ofMillis(1000),
                () -> helloWorld.longFoo()
        );

       int[] foo = helloWorld.longFoo();
        for (int i = 0; i < foo.length - 1; i++) {
            assertTrue(foo[i] <= foo[i + 1]);
        }
    }
}