package oceanstars.ecommerce.common.data;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;

/**
 * 有向环数据结构
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:12 下午
 */
public class DirectedCycle<T> {

  /**
   * 有向环顶点数据栈
   */
  private Deque<T> cycle;

  /**
   * 标记顶点
   */
  private final Set<T> markedNodes;

  /**
   * 递归调用的栈上所有的顶点
   */
  private final Set<T> onStackNodes;

  /**
   * 领接边终点
   */
  private final Map<T, T> edgeTo;

  /**
   * 构造函数
   *
   * @param graph 有向图
   */
  public DirectedCycle(DirectedGraph<T> graph) {
    this.markedNodes = new HashSet<>();
    this.onStackNodes = new HashSet<>();
    this.edgeTo = new HashMap<>();
    Set<T> nodeSet = graph.getGraph().keySet();
    if (!CollectionUtils.isEmpty(nodeSet)) {
      for (T node : nodeSet) {
        this.dfs(graph, node);
      }
    }
  }

  /**
   * 构造函数
   *
   * @param graph 有向图
   * @param node  指定起始顶点
   */
  public DirectedCycle(DirectedGraph<T> graph, T node) {
    this.markedNodes = new HashSet<>();
    this.onStackNodes = new HashSet<>();
    this.edgeTo = new HashMap<>();
    this.dfs(graph, node);
  }

  /**
   * 判定是否有环
   *
   * @return 判定结果
   */
  public Boolean hasCircle() {
    return cycle != null;
  }

  /**
   * 获取有向环
   *
   * @return 有向环
   */
  public Iterable<T> cycle() {
    return cycle;
  }

  /**
   * 获取标记节点
   *
   * @return 标记节点
   */
  public Set<T> markedNodes() {
    return markedNodes;
  }

  /**
   * <p>在进行深度优先搜索的过程中不断把所有顶点放入栈中<p/>
   * <p>如果发现曾经已经存在栈中的顶点再次被别的顶点指向<p/>
   * <p>说明在这条有向路径中出现了一个环<p/>
   * <p>（例如1->5,5->4,4->3,3->5  在再次放入5的过程中就发现了这个环5，4，3）<p/>
   * <p>我们可以通过edgeTo将这个环的路径找到<p/>
   *
   * @param graph 有向图
   * @param node  顶点
   */
  private void dfs(DirectedGraph<T> graph, T node) {
    this.markedNodes.add(node);
    this.onStackNodes.add(node);
    // 获取领接顶点
    final Set<T> adjNodes = graph.getGraph().get(node);

    if (!CollectionUtils.isEmpty(adjNodes)) {
      for (T adjNode : adjNodes) {

        // 发现有向环就结束循环
        if (Boolean.TRUE.equals(this.hasCircle())) {
          return;
        }
        // 如果此顶点未被搜索，进入此顶点的DFS
        else if (!this.markedNodes.contains(adjNode)) {
          this.edgeTo.put(adjNode, node);
          this.dfs(graph, adjNode);
        }
        // 如果此顶点已经存在于递归栈中，说明此为环线
        else if (this.onStackNodes.contains(adjNode)) {
          this.cycle = new ArrayDeque<>();
          for (T tempNode = node; !tempNode.equals(adjNode); tempNode = this.edgeTo.get(tempNode)) {
            this.cycle.push(tempNode);
          }
          this.cycle.push(adjNode);
          this.cycle.push(node);
        }
      }
    }
    // 结束这条路径的环的查找
    this.onStackNodes.remove(node);
  }
}
