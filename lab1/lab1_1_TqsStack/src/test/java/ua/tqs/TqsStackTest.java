package ua.tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

class TqsStackTest {

    private TqsStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new TqsStack<>();
    }

    @Test
    void testStackIsEmptyOnConstruction() {
        assertTrue(stack.isEmpty(), "A pilha deve estar vazia ao ser criada");
    }

    @Test
    void testStackHasSizeZeroOnConstruction() {
        assertEquals(0, stack.size(), "O tamanho inicial da pilha deve ser 0");
    }

    @Test
    void testPushMakesStackNotEmpty() {
        stack.push(10);
        assertFalse(stack.isEmpty(), "A pilha não deve estar vazia após um push");
        assertEquals(1, stack.size(), "O tamanho da pilha deve ser 1 após um push");
    }

    @Test
    void testPushAndPopReturnSameValue() {
        stack.push(5);
        assertEquals(5, stack.pop(), "O valor retornado pelo pop deve ser igual ao último push");
    }

    @Test
    void testPushAndPeek() {
        stack.push(7);
        assertEquals(7, stack.peek(), "O valor retornado pelo peek deve ser igual ao último push");
        assertEquals(1, stack.size(), "O tamanho da pilha não deve mudar após um peek");
    }

    @Test
    void testMultiplePopsMakeStackEmpty() {
        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.pop();
        assertTrue(stack.isEmpty(), "A pilha deve estar vazia após remover todos os elementos");
        assertEquals(0, stack.size(), "O tamanho da pilha deve ser 0 após remover todos os elementos");
    }

    @Test
    void testPopOnEmptyStackThrowsException() {
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void testPeekOnEmptyStackThrowsException() {
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @Test
    void testPopTopN() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(1, stack.popTopN(3), "O terceiro item inserido deve ser retornado");
    }

}

