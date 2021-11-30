package fun.gengzi.codecopy.suanfa;

import org.junit.Test;


public class TreeDepthTest {

    @Test
    public void fun01() {

        //构建一个二叉树
        TreeNode treeNode = new TreeNode(1);
        treeNode.setLeft(new TreeNode(2));
        treeNode.setRight(new TreeNode(3));
        TreeNode treenode2 = treeNode.getLeft();
        treenode2.setLeft(new TreeNode(4));
        treenode2.setRight(new TreeNode(5));
        TreeNode treenode3 = treeNode.getRight();
        treenode3.setRight(new TreeNode(6));
        TreeNode treenode5 = treenode2.getRight();
        treenode5.setRight(new TreeNode(7));

        TreeDepthTest treeDepthTest = new TreeDepthTest();
        int i = treeDepthTest.TreeDepth(treeNode);
        System.out.println(i);
    }

    public int TreeDepth(TreeNode root) {
        // 求深度，从跟节点出发，查左子树的长度  和 右子树的长度 ，对比，那个长选那个
        // 递归 ，递归前进段： 是否还有子节点
        //       递归返回段： 当前节点下无子节点
        //       递归终止： 无子节点
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        int left = TreeDepth(root.left);
        int right = TreeDepth(root.right);
        return left >= right ? left+1 : right+1;

    }

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }


    public int cutRope(int target) {
        /**
         * 给你一根长度为n的绳子，请把绳子剪成整数长的m段（m、n都是整数，n>1并且m>1，m<=n），
         * 每段绳子的长度记为k[1],...,k[m]。请问k[1]x...xk[m]可能的最大乘积是多少？
         * 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
         */
        int[] f = new int[target+1];
        f[0] = 1;
        f[1] = 1;
        for (int i = 0; i <= target; i++) {
            if(i == target){
                f[i] = 1;
            }else{
                f[i] = target;
            }
            for (int j = 1; j < i; j++) {
                f[i] = Math.max(f[i],f[j]*f[i-j]);
            }


        }
        return f[target];

//        target = 5;
         // 1 1 1 1 1
        // 1 2 1 1
        // 1 3 1
        // 1 4
        // 2 1 1 1
        // 2 2 1
        // 2 3
        //3 1 1
        // 3 2
        // 4 1
        // 5


    }
}


