data Tree a = Empty | Node a (Tree a) (Tree a)   
    deriving (Show,Eq,Ord)

create_tree :: (Eq a,Ord a) => a -> Tree a
create_tree a = Node a Empty Empty

add_left :: (Eq a,Ord a) => Tree a -> a -> Tree a
add_left Empty a = create_tree a
add_left (Node e l r) a = Node e (add_right l a) r  
                        

add_right :: (Eq a,Ord a) => Tree a -> a -> Tree a
add_right Empty a = create_tree a
add_right (Node e l r) a = Node e l (add_right r a)  
                        

count_level :: Tree a -> Int
count_level Empty = 0
count_level (Node _ l r) = 1 + max (count_level l) (count_level r)

tree_map :: (a -> a) -> Tree a -> Tree a
tree_plus f Empty = Empty
tree_plus f (Node e l r) = Node r (tree_plus f l) (tree_plus f l)
    where 
        r = f e