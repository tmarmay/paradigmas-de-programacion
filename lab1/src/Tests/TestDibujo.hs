import Test.HUnit
import Dibujo (Dibujo(..), figura, juntar, apilar, rot45, rotar, encimar, espejar,
                r180, r270,
                cuarteto, encimar4, ciclar,
                foldDib, mapDib,
                figuras)

-- Define your test cases
testFigura :: Test
testFigura = TestCase $ do
    let d = figura 'a'
    assertEqual "figura Failure" (Figura 'a') d

testRotar :: Test
testRotar = TestCase $ do
    let d = rotar (figura 'a')
    assertEqual "rotar Failure" (Rotar (Figura 'a')) d

testEspejar :: Test
testEspejar = TestCase $ do
    let d = espejar (figura 'a')
    assertEqual "espejar Failure" (Espejar (Figura 'a')) d

testRot45 :: Test
testRot45 = TestCase $ do
    let d = rot45 (figura 'a')
    assertEqual "rot45 Failure" (Rot45 (Figura 'a')) d

testApilar :: Test
testApilar = TestCase $ do
    let d = apilar 1.0 2.0 (figura 'a') (figura 'b')
    assertEqual "apilar Failure" (Apilar 1.0 2.0 (Figura 'a') (Figura 'b')) d

testJuntar :: Test
testJuntar = TestCase $ do
    let d = juntar 1.0 2.0 (figura 'a') (figura 'b')
    assertEqual "juntar Failure" (Juntar 1.0 2.0 (Figura 'a') (Figura 'b')) d

testEncimar :: Test
testEncimar = TestCase $ do
    let d = encimar (figura 'a') (figura 'b')
    assertEqual "encimar Failure" (Encimar (Figura 'a') (Figura 'b')) d

testRotarEspejar :: Test
testRotarEspejar = TestCase $ do
    let d = rotar (espejar (figura 'a'))
    assertEqual "rotarEspejar Failure" (Rotar (Espejar (Figura 'a'))) d

testRot45Encimar :: Test
testRot45Encimar = TestCase $ do
    let d = rot45 (encimar (figura 'a') (figura 'b'))
    assertEqual "rot45Encimar Failure" (Rot45 (Encimar (Figura 'a') (Figura 'b'))) d

testApilarJuntar :: Test
testApilarJuntar = TestCase $ do
    let d = apilar 1.0 1.0 (juntar 1.0 1.0 (figura 'a') (figura 'b')) (juntar 1.0 1.0 (figura 'c') (figura 'd'))
    assertEqual "apilarJuntar Failure"
        (Apilar 1.0 1.0 (Juntar 1.0 1.0 (Figura 'a') (Figura 'b')) (Juntar 1.0 1.0 (Figura 'c') (Figura 'd'))) d

testR180 :: Test
testR180 = TestCase $ do
    let d = r180 (figura 'a')
    assertEqual "r180 Failure" (Rotar (Rotar (Figura 'a'))) d

testR270 :: Test
testR270 = TestCase $ do
    let d = r270 (figura 'a')
    assertEqual "r270 Failure" (Rotar (Rotar (Rotar (Figura 'a')))) d

testCiclar :: Test
testCiclar = TestCase $ do
    let d = ciclar (figura 'a')
    assertEqual "ciclar Failure" (cuarteto (Figura 'a') (Rotar (Figura 'a')) (r180 (Figura 'a')) (r270 (Figura 'a'))) d

testEncimar4 :: Test
testEncimar4 = TestCase $ do
    let d = encimar4 (figura 'a')
    assertEqual "encimar4 Failure"
        (Encimar (Figura 'a') (Encimar (Rotar (Figura 'a')) ((Encimar (r180 (Figura 'a'))) (r270(Figura 'a'))))) d

testMapDib :: Test
testMapDib = TestCase $ do
    let d = mapDib (\c -> if (c=='a') then (figura 'd') else (figura 'c')) (encimar (figura 'b') (rotar (ciclar (figura 'a'))))
    assertEqual "mapDib Failure"
        (Encimar (Figura 'c') (Rotar (cuarteto (Figura 'd') (Rotar (Figura 'd')) (r180 (Figura 'd')) (r270 (Figura 'd'))))) d

testFiguras :: Test
testFiguras = TestCase $ do
    let d = figuras (encimar (rotar (figura 'a')) (encimar (rot45 (figura 'b')) (figura 'c')))
    assertEqual "figuras Failure"
        ['a', 'b', 'c'] d

-- Create a test suite and run the tests
tests :: Test
tests = TestList [ testFigura
                 , testRotar
                 , testEspejar
                 , testRot45
                 , testApilar
                 , testJuntar
                 , testEncimar
                 , testRotarEspejar
                 , testRot45Encimar
                 , testApilarJuntar
                 , testR180
                 , testR270
                 , testCiclar
                 , testEncimar4
                 , testMapDib
                 , testFiguras
                 ]

main :: IO Counts
main = runTestTT tests